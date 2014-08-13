package br.com.caelum.fj59.carangos.tasks;

import java.util.List;

import br.com.caelum.fj59.carangos.CarangosApplication;
import br.com.caelum.fj59.carangos.modelo.BlogPost;

/**
 * Created by android4521 on 02/08/14.
 */
public interface BuscaMaisPostsDelegate
{
    void lidaComRetorno(List<BlogPost> retorno);
    void lidaComErro(Exception e);

    CarangosApplication getCarangosApplication();
}