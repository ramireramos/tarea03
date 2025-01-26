package ramirezramos.francisco.tarea03;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class AuthActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 100;
    private static final String TAG = "AuthActivity";

    private EditText etEmail, etPassword;
    private FirebaseAuth auth;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity);

        // Inicializo Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Configuro Google Sign-In
        initGoogleSignInClient();

        // Inicializo las vistas y configuro los botones
        initViews();
    }

    // Configuro el cliente de Google Sign-In con las opciones necesarias
    private void initGoogleSignInClient() {
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // Solicito el token OAuth
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, options);
    }

    // Inicializo las vistas y asigno los listeners a los botones
    private void initViews() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        findViewById(R.id.btnRegister).setOnClickListener(v -> registerUser());
        findViewById(R.id.btnLogin).setOnClickListener(v -> loginUser());
        findViewById(R.id.btnLoginGoogle).setOnClickListener(v -> signInWithGoogle());
    }

    // Registro un nuevo usuario con email y contraseña
    private void registerUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (validateInputs(email, password)) {
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Usuario registrado con éxito
                            showToast("Usuario registrado: " + auth.getCurrentUser().getEmail());
                            goToMainActivity();
                        } else {
                            // Error al registrar usuario
                            showToast("Error al registrar: " + task.getException().getMessage());
                        }
                    });
        }
    }

    // Inicio sesión con un usuario existente usando email y contraseña
    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (validateInputs(email, password)) {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Inicio de sesión exitoso
                            showToast("Bienvenido: " + auth.getCurrentUser().getEmail());
                            goToMainActivity();
                        } else {
                            // Error al iniciar sesión
                            showToast("Error al iniciar sesión: " + task.getException().getMessage());
                        }
                    });
        }
    }

    // Inicio sesión con Google
    private void signInWithGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // Manejo el resultado de Google Sign-In
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            try {
                // Obtengo la cuenta seleccionada
                GoogleSignInAccount account = GoogleSignIn.getSignedInAccountFromIntent(data).getResult(ApiException.class);
                if (account != null) {
                    firebaseAuthWithGoogle(account);
                }
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
                showToast("Error al iniciar sesión con Google");
            }
        }
    }

    // Autentico al usuario con Firebase usando la cuenta de Google
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        auth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Inicio de sesión exitoso
                showToast("Bienvenido: " + auth.getCurrentUser().getDisplayName());
                goToMainActivity();
            } else {
                // Error en la autenticación con Firebase
                Log.w(TAG, "signInWithCredential:failure", task.getException());
                showToast("Error al autenticar con Firebase");
            }
        });
    }

    // Valido los campos de entrada para email y contraseña
    private boolean validateInputs(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            showToast("Por favor, llena todos los campos");
            return false;
        }
        return true;
    }

    // Navego a la pantalla principal de la aplicación
    private void goToMainActivity() {
        Intent intent = new Intent(AuthActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    // Muestro un mensaje en forma de Toast
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
