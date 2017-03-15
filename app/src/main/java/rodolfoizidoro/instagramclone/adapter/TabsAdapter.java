package rodolfoizidoro.instagramclone.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.ViewGroup;

import java.util.HashMap;

import rodolfoizidoro.instagramclone.R;
import rodolfoizidoro.instagramclone.fragments.HomeFragment;
import rodolfoizidoro.instagramclone.fragments.UsuariosFragment;

/**
 * Created by rodolfoizidoro on 09/03/17.
 */

public class TabsAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private int[] icones = new int[]{R.drawable.ic_action_home,R.drawable.ic_people};
    private int tamanhoIcone;
    private HashMap<Integer, Fragment> fragmentosUtilizados = new HashMap<>();

    public TabsAdapter(FragmentManager fm, Context cx) {
        super(fm);
        context = cx;
        double escala = context.getResources().getDisplayMetrics().density;
        tamanhoIcone = (int) (48 * escala);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new HomeFragment();
                fragmentosUtilizados.put(position,fragment);
                break;
            case 1:
                fragment = new UsuariosFragment();
                break;

        }
        return fragment;
    }

    public Fragment getFragment(Integer indice){

    return fragmentosUtilizados.get(indice);

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        fragmentosUtilizados.remove(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {

        Drawable drawable = ContextCompat.getDrawable(context,icones[position]);
        drawable.setBounds(0,0,tamanhoIcone,tamanhoIcone);


        //permite colocar uma imagem dentro de um texto
        ImageSpan imageSpan = new ImageSpan(drawable);

        //classe utilizar para charsequence
        SpannableString spannableString = new SpannableString(" ");
        spannableString.setSpan(imageSpan,0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;

    }

    @Override
    public int getCount() {
        return icones.length;
    }
}
