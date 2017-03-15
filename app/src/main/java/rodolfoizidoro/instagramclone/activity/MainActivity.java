package rodolfoizidoro.instagramclone.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import rodolfoizidoro.instagramclone.R;
import rodolfoizidoro.instagramclone.adapter.TabsAdapter;
import rodolfoizidoro.instagramclone.fragments.HomeFragment;
import rodolfoizidoro.instagramclone.util.SlidingTabLayout;

public class MainActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //configura Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbarPrincipal);
        toolbar.setLogo(R.drawable.instagramlogo);
        setSupportActionBar(toolbar);

        //Configura abas
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.slidingTabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        //configurar tabs adapter
        TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager(),MainActivity.this);
        viewPager.setAdapter(tabsAdapter);
        slidingTabLayout.setCustomTabView(R.layout.tab_view,R.id.text_item_tab);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(MainActivity.this,R.color.colorPrimaryDark));
        slidingTabLayout.setViewPager(viewPager);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_sair:
                deslogarUsuario();
                return true;

            case R.id.action_configuracoes:
                return true;

            case R.id.action_compartilhar:
                compartilharFoto();
                return true;

            default:
                return super.onOptionsItemSelected(item);


        }

    }

    private void compartilharFoto(){

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Testar processo de retorno dos dados
        if( requestCode==1 && resultCode == RESULT_OK && data != null ){

            //recuperar local do recurso
            Uri localImagemSelecionada = data.getData();

            //recupera a imagem do local que foi selecionada
            try {
                Bitmap imagem = MediaStore.Images.Media.getBitmap( getContentResolver(), localImagemSelecionada );

                //comprimir no formato PNG
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imagem.compress(Bitmap.CompressFormat.PNG, 75, stream);
                //Cria um array de bytes da imagem
                byte[] byteArray = stream.toByteArray();

                //Criar um arquivo com formato próprio do parse
                SimpleDateFormat dateFormat = new SimpleDateFormat("ddmmaaaahhmmss");
                String nomeImagem = dateFormat.format( new Date() );
                ParseFile arquivoParse = new ParseFile(nomeImagem +"imagem.png", byteArray);

                //Monta objeto para salvar no parse
                ParseObject parseObject = new ParseObject("Imagem");
                parseObject.put("username", ParseUser.getCurrentUser().getUsername() );
                parseObject.put("imagem", arquivoParse );

                //salvar os dados
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if( e==null ){//sucesso
                            Toast.makeText(getApplicationContext(), "Sua imagem foi postada!!!", Toast.LENGTH_LONG ).show();

                       //Atualiza o framento
                            TabsAdapter adapterNovo = (TabsAdapter) viewPager.getAdapter();
                            HomeFragment homeFragmentNovo = (HomeFragment) adapterNovo.getFragment(0);
                            homeFragmentNovo.atualizaPostagens();


                        }else{//erro
                            Toast.makeText(getApplicationContext(), "Erro ao postar sua imagem - Tente novamente!",
                                    Toast.LENGTH_LONG ).show();
                        }

                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private void deslogarUsuario(){

        ParseUser.logOut();

        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();

    }
}
