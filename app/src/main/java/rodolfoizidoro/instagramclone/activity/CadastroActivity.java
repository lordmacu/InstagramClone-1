package rodolfoizidoro.instagramclone.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import rodolfoizidoro.instagramclone.R;
import rodolfoizidoro.instagramclone.util.ParseErros;

public class CadastroActivity extends AppCompatActivity {

    private EditText etUsuario;
    private EditText etEmail;
    private EditText etSenha;
    private Button btCadastrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        etUsuario = (EditText) findViewById(R.id.etNomeUsuario);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etSenha = (EditText) findViewById(R.id.etSenha);
        btCadastrar = (Button) findViewById(R.id.btCadastrar);

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cadastrarUsuario(etUsuario.getText().toString(), etEmail.getText().toString(), etSenha.getText().toString());
            }
        });


    }

    private void cadastrarUsuario(String nome, String email, String senha) {
        //Cria objeto usuario
        ParseUser usuario = new ParseUser();
        usuario.setUsername(nome);
        usuario.setEmail(email);
        usuario.setPassword(senha);

        usuario.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) { // sucesso ao salvar

                    Toast.makeText(CadastroActivity.this, "Sucesso ao cadastrar", Toast.LENGTH_SHORT).show();
                    abrirLoginUsuario();
                } else { //erro ao salvar
                    ParseErros parseErros = new ParseErros();


                    Toast.makeText(CadastroActivity.this, parseErros.getErro(e.getCode()), Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    public void abrirLoginUsuario() {
        Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();

    }
}
