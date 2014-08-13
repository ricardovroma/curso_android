package br.com.caelum.fj59.carangos;

import android.app.Application;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.fj59.carangos.modelo.BlogPost;

/**
 * Created by android4521 on 02/08/14.
 */
public class CarangosApplication extends Application {

    private List<BlogPost> posts = new ArrayList<BlogPost>();
    private List<AsyncTask<?, ?, ?>> tasks = new ArrayList<AsyncTask<?, ?, ?>>();

    public void registra(AsyncTask<?, ?, ?> task){
        tasks.add(task);
    }

    public void desregistra(AsyncTask<? , ?, ?> task) {
        tasks.remove(task);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        for (AsyncTask task : this.tasks){
            task.cancel(true);
        }
    }

    public List<BlogPost> getPosts(){
        return posts;
    }
}
