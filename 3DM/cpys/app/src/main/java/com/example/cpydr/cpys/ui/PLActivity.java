package com.example.cpydr.cpys.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cpydr.cpys.R;
import com.example.cpydr.cpys.adapter.ComentAdapter;
import com.example.cpydr.cpys.entity.Comment;
import com.example.cpydr.cpys.entity.Game;
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

public class PLActivity extends Activity {
    private List<Comment> comments;
    private ComentAdapter comentAdapter;
    private HttpUtils httpUtils;
    private SuperClass aClass;
    private int page=1;
    private String path;
    private PullToRefreshListView listView;
    private ProgressBar progressBar;
    private ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pl);
        //初始化
        listView = (PullToRefreshListView)findViewById(R.id.PL_listView);
        comments = new ArrayList<Comment>();
        imageButton = (ImageButton)findViewById(R.id.PL_back);
        comentAdapter = new ComentAdapter(comments,PLActivity.this);
        listView.setAdapter(comentAdapter);
        httpUtils =  new HttpUtils();
        aClass = new SuperClass();
        path="http://www.3dmgame.com/sitemap/api.php?type=1&aid="+aClass.getArticle().getId()+"&pageno=";
        progressBar= (ProgressBar)findViewById(R.id.PL_progressBar);




        listView.setEmptyView(progressBar);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                comments.clear();
                getdata();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getdata();
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PLActivity.this.finish();
            }
        });
        getdata();
    }

    public void getdata(){
        httpUtils.send(HttpRequest.HttpMethod.GET, path + page, new RequestCallBack<String>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String res = responseInfo.result;
                try {
                    JSONObject json = new JSONObject(res);
                    Log.i("cpy>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>",json+path +page+comments.size());
//                    JSONObject description = json.getJSONObject("description");
//                    JSONObject data = description.getJSONObject("data");
//                    for (int i = 0; i <10 ; i++) {
//                        JSONObject jsongame = data.getJSONObject(""+i);
//                        Comment comment = new Comment();
//                        comment.parseJSON(jsongame);
//                        Log.i("cpygame>>>>>>>>",aClass.getArticle().getId()+ comment.getMsg());
//                        comments.add(comment);
//                    }
//
                    Comment comment = new Comment();
                    comment.setFloor("0");
                    comment.setId("1");
                    comment.setMsg("对不起！我们获取不了数据");
                    comment.setUsername("系统");
                    comments.add(comment);

                    comentAdapter.notifyDataSetChanged();
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
