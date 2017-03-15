package rodolfoizidoro.instagramclone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import rodolfoizidoro.instagramclone.R;
import rodolfoizidoro.instagramclone.adapter.HomeAdapter;

public class FeedUsuariosActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    private String userName;
    private ArrayAdapter<ParseObject> adapter;
    private ArrayList<ParseObject> postagens;
    private ParseQuery<ParseObject> query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_usuarios);

        Intent intent = getIntent();
        userName = intent.getStringExtra("username");

        toolbar = (Toolbar) findViewById(R.id.toolbar_feed_usuarios);

        toolbar.setTitle(userName);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.preto));
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left);
        setSupportActionBar(toolbar);

        postagens = new ArrayList<>();
        listView = (ListView) findViewById(R.id.list_feed_usuarios);
        adapter = new HomeAdapter(getApplicationContext(), postagens);
        listView.setAdapter(adapter);

        getPostagens();


    }

    private void getPostagens() {

        query = ParseQuery.getQuery("Imagem");
       // query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {

                    if(objects.size() > 0){
                        postagens.clear();
                        for(ParseObject parseObject : objects){

                            postagens.add(parseObject);

                        }
                        adapter.notifyDataSetChanged();
                    }


                } else {
                }
            }
        });


    }

}
