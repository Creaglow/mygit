package com.example.cpydr.cpys.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cpydr.cpys.R;
import com.example.cpydr.cpys.adapter.GameAdapter;
import com.example.cpydr.cpys.entity.Game;
import com.example.cpydr.cpys.ui.TowActivity;
import com.example.cpydr.cpys.ui.WenActivity;
import com.example.cpydr.cpys.util.SuperClass;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class GameFragment extends Fragment {
    private Spinner spinner;
    private List<String> list;
    private List<Game> games;
    private PullToRefreshGridView gridView;
    private SuperClass aClass;
    private ProgressBar progressBar;
    private int page=1;
    private HttpUtils httpUtils;
    private GameAdapter gameAdapter;
    private String path;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game,container,false);
        progressBar = (ProgressBar)v.findViewById(R.id.progressBar3);
        spinner = (Spinner)v.findViewById(R.id.game_spinner);
        gridView = (PullToRefreshGridView)v.findViewById(R.id.game_gridView);
        gridView.setEmptyView(progressBar);
        list = new ArrayList<String>();
        list.add("动作");
        list.add("射击");
        list.add("角色扮演");
        list.add("养成");
        list.add("益智");
        list.add("即时战略");
        list.add("策略");
        list.add("体育");
        list.add("模拟经营");
        list.add("赛车");
        list.add("冒险");
        list.add("动作角色");
        spinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, list));
        httpUtils = new HttpUtils();
        aClass = new SuperClass();
        games = new ArrayList<Game>();
        gameAdapter = new GameAdapter(getActivity(),games);
        gridView.setAdapter(gameAdapter);
        path=aClass.getGamePath(179)+page;
        getData();

        //点击事件处理
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //数据初始化
                games.clear();
                page= 1;
                switch (position){
                    case 0:
                        path=aClass.getGamePath(181)+page;

                        break;
                    case 1:
                        path=aClass.getGamePath(182)+page;
                        break;
                    case 2:
                        path=aClass.getGamePath(183)+page;
                        break;
                    case 3:
                        path=aClass.getGamePath(184)+page;
                        break;
                    case 4:
                        path=aClass.getGamePath(185)+page;
                        break;
                    case 5:
                        path=aClass.getGamePath(186)+page;
                        break;
                    case 6:
                        path=aClass.getGamePath(187)+page;
                        break;
                    case 7:
                        path=aClass.getGamePath(188)+page;
                        break;
                    case 8:
                        path=aClass.getGamePath(189)+page;
                        break;
                    case 9:
                        path=aClass.getGamePath(190)+page;
                        break;
                    case 10:
                        path=aClass.getGamePath(191)+page;
                        break;
                    case 11:
                        path=aClass.getGamePath(192)+page;
                        break;
                }
                getData();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        gridView.setMode(PullToRefreshBase.Mode.BOTH);
        gridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                games.clear();
                page = 1;
                getData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                page++;
                getData();
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                aClass.setGame(games.get(position));
                Intent intent=new Intent();
                intent.setClass(getActivity(), TowActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }


    public void  getData(){
        httpUtils.send(HttpRequest.HttpMethod.GET, path, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String res = responseInfo.result;
                res = res.substring(res.indexOf("{"));
                try {
                    JSONObject json = new JSONObject(res);
                    JSONObject data = json.getJSONObject("data");
                    for (int i = 0; i <20 ; i++) {
                        JSONObject jsongame = data.getJSONObject(""+i);
                        Game game = new Game();
                        game.parase(jsongame);
                        Log.i("cpygame>>>>>>>>", game.getShorttitle());
                        games.add(game);
                    }
                    gameAdapter.notifyDataSetChanged();
                    gridView.onRefreshComplete();
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
