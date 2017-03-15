package rodolfoizidoro.instagramclone.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import rodolfoizidoro.instagramclone.R;
import rodolfoizidoro.instagramclone.activity.FeedUsuariosActivity;
import rodolfoizidoro.instagramclone.adapter.UsuariosAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsuariosFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter<ParseUser> adapter;
    private ArrayList<ParseUser> usuarios;
    private ParseQuery<ParseUser> query;

    public UsuariosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_uuarios, container, false);
        usuarios = new ArrayList<>();
        listView = (ListView) view.findViewById(R.id.list_usuarios);
        adapter = new UsuariosAdapter(getActivity(),usuarios);
        listView.setAdapter(adapter);

        //recuperar os usuarios
        getUsuarios();

        //colocar o evento click nos itens da lista

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ParseUser parseUser = usuarios.get(position);

                //enviar dados para o feed usuarios
                Intent intent = new Intent(getActivity(), FeedUsuariosActivity.class);
                intent.putExtra("username",parseUser.getUsername());

                startActivity(intent);
            }
        });

        return view;
    }

    private void getUsuarios(){
        //recuperar lista de usuarios
        query = ParseUser.getQuery();
        query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        query.orderByAscending("username");
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if( e == null){
                    if(objects.size() > 0){
                        usuarios.clear();

                        for(ParseUser parseUser : objects){
                            usuarios.add(parseUser);
                        }
                        adapter.notifyDataSetChanged();

                    }
                }
                else {
                    e.printStackTrace();
                }
            }
        });


    }

}
