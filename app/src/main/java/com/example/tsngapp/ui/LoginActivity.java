package com.example.tsngapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tsngapp.R;
import com.example.tsngapp.helpers.StateManager;
import com.example.tsngapp.api.SMARTAAL;
import com.example.tsngapp.helpers.Constants;
import com.example.tsngapp.helpers.ErrorCode;
import com.example.tsngapp.helpers.ErrorValidator;
import com.example.tsngapp.helpers.JsonConverterSingleton;
import com.example.tsngapp.model.DataToSend;
import com.example.tsngapp.model.Elder;
import com.example.tsngapp.model.User;
import com.example.tsngapp.network.AsyncGetAuthTask;
import com.example.tsngapp.network.AsyncTaskAuthenticationPost;
import com.example.tsngapp.network.OnFailureListener;
import com.example.tsngapp.network.OnResultListener;
import com.example.tsngapp.view_managers.LoginManager;


public class LoginActivity extends AppCompatActivity {

    private final String LOG_TAG = "LoginActivity";

    private EditText usernameEditText;
    private EditText passwordEditText;
    private AsyncTaskAuthenticationPost loginTask;
    private AsyncGetAuthTask getUserTask;
    private User user;
    private LinearLayout progress_Layout;
    private TextView progressaLayoutTextView;
    private TextView tvNoAccountBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Ver ligação à Internet
        if (!ErrorValidator.getInstance().checkInternetConnection(this)) {
            ErrorValidator.getInstance().showErrorMessage(this, "Please activate your connection to Internet");
            return;
        }

        findViewById(R.id.loginBtn).setOnClickListener(v -> startLogin());

        this.usernameEditText = findViewById(R.id.et_email_login);
        this.passwordEditText = findViewById(R.id.et_password_login);
        this.progress_Layout = findViewById(R.id.progress_layout);
        this.progressaLayoutTextView = findViewById(R.id.progressLayoutTextView);
        this.tvNoAccountBtn = findViewById(R.id.tv_no_account_button);

        this.passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                startLogin();
                return true;
            }
            // Return true if you have consumed the action, else false.
            return false;
        });


        tvNoAccountBtn.setOnClickListener(v -> {
            startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class),
                    Constants.REGISTER_ACTIVITY_CODE);
        });

        //Ve se token ainda esta valido para autenticação
        String token = LoginManager.getInstance().retrieveAuthToken(this);
        if (!token.isEmpty()) performPostAuthenticationActions(token);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.initial_menu, menu);

        menu.getItem(0).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.action_signIn) {
            setupRegisterActivity();
        }

        return true;
    }

    private void setupRegisterActivity() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);

        startActivityForResult(intent, Constants.REGISTER_ACTIVITY_CODE);
        //startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        //this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REGISTER_ACTIVITY_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                user = (User) data.getSerializableExtra(Constants.INTENT_USER_KEY);
                String password = data.getStringExtra(Constants.INTENT_PASSWORD_KEY);
                prepareLogin(user.getEmail(), password);
            }
        }
    }

    private void startLogin() {
        //Esconde o teclado
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            Log.d(LOG_TAG, "Failed keyboard hidding" + e.getMessage());
        }

        //Obtém password e username, valida e cria json para enviar no pedido
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        prepareLogin(username, password);
    }

    private void prepareLogin(String username, String password) {
        final DataToSend dataToSend = LoginManager.getInstance().generateJsonForPost(username, password);

        if (dataToSend.getErrorCodes().size() > 0) {
            for (ErrorCode e : dataToSend.getErrorCodes()) {
                if (e == ErrorCode.EMAIL_EMPTY) {
                    usernameEditText.setError(ErrorValidator.getInstance().getErrorMessage(e));
                }
                if (e == ErrorCode.PASSWORD_EMPTY) {
                    passwordEditText.setError(ErrorValidator.getInstance().getErrorMessage(e));
                }
            }

            return;
        }

        performLogin(dataToSend);
    }

    private void performLogin(DataToSend dataToSend) {
        this.loginTask = new AsyncTaskAuthenticationPost(dataToSend.getJsonObject(), jsonString -> {
            progress_Layout.setVisibility(View.INVISIBLE);

            if (jsonString == null || jsonString.isEmpty()) {
                ErrorValidator.getInstance().showErrorMessage(LoginActivity.this, "An error ocurred during the login");
                return;
            }

            String token = LoginManager.getInstance().getTokenFromJson(jsonString);

            //Vai buscar a informação do utilizador e do elder e continua o login
            performPostAuthenticationActions(token);
        });

        this.progressaLayoutTextView.setText("Login user");
        this.progress_Layout.setVisibility(View.VISIBLE);
        this.loginTask.execute(Constants.LOGIN_URL);
    }

    private void performPostAuthenticationActions(String token) {
        getUserInfo(token, user -> {
            user.setAccessToken(token);
            getElderInfo(user.getId(), token,
                elder -> {
                    StateManager.getInstance()
                            .setUser(user)
                            .setElder(elder);
                    LoginManager.getInstance()
                            .saveAuthInfo(token, user, elder, this);
                    performPostLoginActions();
                },
                e -> handleLoginFailed("Couldn't get elder, " + e.getMessage())
            );
        }, this::handleLoginFailed);
    }

    private void getUserInfo(final String token, OnResultListener<User> resultListener,
                             OnFailureListener failureListener) {
        this.getUserTask = new AsyncGetAuthTask(token, jsonString -> {
            progress_Layout.setVisibility(View.GONE);

            //Converte json em User
            user = JsonConverterSingleton.getInstance().jsonToUser(jsonString, false);
            if (user == null) {
                failureListener.onFailure(new NullPointerException("Couldn't get user (got null)"));
                return;
            }

            resultListener.onResult(user);
        });

        this.progress_Layout.setVisibility(View.VISIBLE);
        this.getUserTask.execute(Constants.USERS_ME_URL);
    }

    private void getElderInfo(int userId, final String token,
                              OnResultListener<Elder> resultListener,
                              OnFailureListener failureListener) {
        new SMARTAAL.ElderInfo(userId, token, resultListener, failureListener).execute();
    }

    private void performPostLoginActions() {
        // Redirect to the LoggedInActivity
        startActivity(new Intent(this, LoggedInActivity.class));
        finish();
    }

    private void handleLoginFailed(Exception e) {
        handleLoginFailed(e.getMessage());
    }

    private void handleLoginFailed(String message) {
        Log.d(Constants.DEBUG_TAG, "Failed to login: " + message);
    }
}
