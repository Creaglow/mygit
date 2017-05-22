package com.example.cpydr.cpys.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.cpydr.cpys.R;
import com.example.cpydr.cpys.adapter.ArticleAdapter;
import com.example.cpydr.cpys.entity.Article;
import com.example.cpydr.cpys.ui.WenActivity;
import com.example.cpydr.cpys.util.SuperClass;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class AriticleNoBannerFragment extends Fragment {
    private PullToRefreshListView listView ;
    private int type;
    private int page=1;
    private HttpUtils httpUtils;
    private String path;
    private SuperClass aClass;
    private ProgressBar progressBar;
    private List<Article> articles;
    private ArticleAdapter adapter;

    public AriticleNoBannerFragment(int type) {
        this.type = type;
        articles = new ArrayList<Article>();
        httpUtils = new HttpUtils();

        path ="http://www.3dmgame.com/sitemap/api.php?row=20&typeid="+this.type+"&paging=1&page=";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        aClass =  new SuperClass();
        View v = inflater.inflate(R.layout.fragment_ariticle_no_banner, container, false);
        progressBar = (ProgressBar)v.findViewById(R.id.progressBar2);

        adapter = new ArticleAdapter(getActivity(),articles);
        listView= (PullToRefreshListView) v.findViewById(R.id.article_rad1_listview);
        listView.setEmptyView(progressBar);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setAdapter(adapter);
        getData(page);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                getData(page);
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                articles.clear();
                page++;
                getData(page);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                aClass.setArticle(articles.get(position-1));
                Intent intent=new Intent();
                intent.setClass(getActivity(), WenActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }
    public  void getData(int page){

        httpUtils.send(HttpRequest.HttpMethod.GET, path+page, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String res = responseInfo.result;
                try {
                    JSONObject json = new JSONObject(res);
                    JSONObject jsondata =  json.getJSONObject("data");
                    for (int i = 0; i <20 ; i++) {
                        JSONObject jsonarticle = jsondata.getJSONObject("" + i);//找对象
                        Article article = new Article();
                        article.parseJSON(jsonarticle);//解析赋值
                        articles.add(article);
                        adapter.notifyDataSetChanged();
                    }
                    listView.onRefreshComplete();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });

    }

}
