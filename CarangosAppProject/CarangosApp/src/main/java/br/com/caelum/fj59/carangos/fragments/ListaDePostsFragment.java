package br.com.caelum.fj59.carangos.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import br.com.caelum.fj59.carangos.CarangosApplication;
import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.activity.MainActivity;
import br.com.caelum.fj59.carangos.adapter.BlogPostAdapter;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.modelo.BlogPost;

import br.com.caelum.fj59.carangos.navegacao.EstadoMainActivity;

/**
 * Created by erich on 9/11/13.
 */
public class ListaDePostsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private ListView postsList;
    private BlogPostAdapter adapter;
    private SwipeRefreshLayout swipe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final MainActivity activity = ((MainActivity)this.getActivity());
        CarangosApplication app = activity.getCarangosApplication();

        swipe = (SwipeRefreshLayout) inflater.inflate(R.layout.posts_list, container, false);
        this.postsList = (ListView) swipe.findViewById(R.id.posts_list);



        this.adapter = new BlogPostAdapter(getActivity(), app.getPosts());
        this.postsList.setAdapter(this.adapter);

        //activity.getAttacher().addRefreshableView(this.postsList, this);
        swipe.setOnRefreshListener(this);
        swipe.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_blue_dark,android.R.color. holo_blue_light, android.R.color.holo_blue_dark);

        return swipe;
    }

//    @Override
//    public void onRefreshStarted(View view) {
//        MyLog.i(" PULL TO REFRESH INICIADO");
//        MainActivity activity = (MainActivity) this.getActivity();
//        activity.alteraEstadoEExecuta(EstadoMainActivity.PULL_TO_REFRESH_REQUISITANDO);
//    }

    @Override
    public void onRefresh() {
        MyLog.i(" PULL TO REFRESH INICIADO");
        MainActivity activity = (MainActivity) this.getActivity();
        activity.alteraEstadoEExecuta(EstadoMainActivity.PULL_TO_REFRESH_REQUISITANDO);

        swipe.setRefreshing(true);
    }
}
