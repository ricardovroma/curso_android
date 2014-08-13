package br.com.caelum.fj59.carangos.activity;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.fj59.carangos.CarangosApplication;
import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.adapter.BlogPostAdapter;
import br.com.caelum.fj59.carangos.evento.EventoBlogPostsRecebidos;
import br.com.caelum.fj59.carangos.fragments.ListaDePostsFragment;
import br.com.caelum.fj59.carangos.fragments.ProgressFragment;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.modelo.BlogPost;
import br.com.caelum.fj59.carangos.navegacao.EstadoMainActivity;
import br.com.caelum.fj59.carangos.tasks.BuscaMaisPostsDelegate;
import br.com.caelum.fj59.carangos.tasks.BuscaMaisPostsTask;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;

public class MainActivity extends Activity implements BuscaMaisPostsDelegate {
    private ListView postsList;
    //private List<BlogPost> posts;
    private BlogPostAdapter adapter;
    private BuscaMaisPostsTask task;
    private EstadoMainActivity estado;
    private static final String ESTADO_ATUAL = "ESTADO_ATUAL";

    private EventoBlogPostsRecebidos evento;

//    private PullToRefreshAttacher attacher;

    @Override
    protected void onResume() {
        super.onResume();

        MyLog.i("EXECUTANDO ESTADO: " + this.estado);
        this.estado.executa(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        MyLog.i("SALVANDO ESTADO");

        outState.putSerializable(ESTADO_ATUAL, this.estado);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        MyLog.i("RESTAURANDO ESTADO");
        this.estado = (EstadoMainActivity) savedInstanceState.getSerializable(ESTADO_ATUAL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

//        this.postsList = (ListView) findViewById(R.id.posts_list);
//        this.posts = new ArrayList<BlogPost>();

        this.estado = EstadoMainActivity.INICIO;
        //this.estado.executa(this);

        // Primeiro registrando a Activity como observador
        this.evento = EventoBlogPostsRecebidos.registraObservador(this);

        //this.attacher = PullToRefreshAttacher.get(this);
    }

    public void buscaPrimeirosPosts(){
        new BuscaMaisPostsTask(getCarangosApplication()).execute();
        Toast.makeText(this, "Buscando dados", Toast.LENGTH_SHORT).show();
    }

    public void alteraEstadoEExecuta(EstadoMainActivity estado){
        this.estado = estado;
        this.estado.executa(this);
    }

//    public List<BlogPost> getPosts() {
//        return this.posts;
//    }

    @Override
    public void lidaComRetorno(List<BlogPost> posts) {
        CarangosApplication application = (CarangosApplication) getApplication();

        application.getPosts().clear();
        application.getPosts().addAll(posts);

        this.estado = EstadoMainActivity.PRIMEIROS_POSTS_RECEBIDOS;
        this.estado.executa(this);

        //this.swipe.setRefreshComplete();
    }

    @Override
    public void lidaComErro(Exception e) {
        Toast.makeText(this, "Erro na busca dos dados", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        //this.task.cancel(true);
        super.onDestroy();
        this.evento.desregistra(getCarangosApplication());
    }

    @Override
    public CarangosApplication getCarangosApplication() {
        return (CarangosApplication) getApplication();
    }
}
