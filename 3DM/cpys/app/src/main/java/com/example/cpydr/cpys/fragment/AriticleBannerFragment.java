package com.example.cpydr.cpys.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.cpydr.cpys.R;
import com.example.cpydr.cpys.adapter.ArticleAdapter;
import com.example.cpydr.cpys.adapter.MyPagerAdapter;
import com.example.cpydr.cpys.entity.Article;
import com.example.cpydr.cpys.ui.WenActivity;
import com.example.cpydr.cpys.util.SuperClass;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.BitmapUtils;
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
public class AriticleBannerFragment extends Fragment {
    private PullToRefreshListView listView;
    private ListView myListView;
    private HttpUtils httpUtils;
    private BitmapUtils bitmapUtils;
    private String path;
    private int page=1;
    private int type;
    private ViewPager picViewPager;
    private MyPagerAdapter myPagerAdapter;
    private ProgressBar progressBar;
    private List<ImageView>  imageViews;
    private List<Article> articles;
    private ArticleAdapter adapter;

    private SuperClass aClass;

    public AriticleBannerFragment(int type) {
        this.type = type;
        httpUtils = new HttpUtils();
        articles = new ArrayList<Article>();
        aClass = new SuperClass();
        path ="http://www.3dmgame.com/sitemap/api.php?row=20&typeid="+this.type+"&paging=1&page=";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_ariticle_banner, container, false);
        progressBar = (ProgressBar)v.findViewById(R.id.progressBar1);




        //广告布局加载
        View viewBanner=LayoutInflater.from(getActivity()).inflate(R.layout.banner_layout, null);
        //图片广告
        picViewPager = (ViewPager)viewBanner.findViewById(R.id.viewpager_banner);
        imageViews = new ArrayList<ImageView>();
        listView = (PullToRefreshListView)v.findViewById(R.id.article_rad0_listview);

        //得到控件
        myListView = listView.getRefreshableView();

        //TODO
        ImageView image1 = new ImageView(getActivity());
        image1.setBackgroundResource(R.drawable.default1);
        imageViews.add(image1);
        ImageView image2 = new ImageView(getActivity());
        image2.setBackgroundResource(R.drawable.default2);
        imageViews.add(image2);
        ImageView image3 = new ImageView(getActivity());
        image3.setBackgroundResource(R.drawable.default3);
        imageViews.add(image3);
        myPagerAdapter = new MyPagerAdapter(imageViews);
        picViewPager.setAdapter(myPagerAdapter);

        myListView.addHeaderView(viewBanner);
        listView.setMode(PullToRefreshBase.Mode.BOTH);


        //设置点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                aClass.setArticle(articles.get(position - 2));
                Intent intent=new Intent();
                intent.setClass(getActivity(), WenActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(), aClass.getArticle().getShortTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                articles.clear();
                page = 1;
                getData(page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getData(page);
            }
        });

        adapter = new ArticleAdapter(getActivity(), articles);
        listView.setAdapter(adapter);
        getData(page);
        listView.setEmptyView(progressBar);
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
                        Log.i("cpy",article.getId());
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
