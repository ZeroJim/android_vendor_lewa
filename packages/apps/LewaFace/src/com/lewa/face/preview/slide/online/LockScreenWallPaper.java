package com.lewa.face.preview.slide.online;

import java.util.ArrayList;

import android.content.Context;
import android.support.lewa.view.PagerAdapter;
import android.util.Log;
import android.view.View;

import com.lewa.face.R;
import com.lewa.face.download.DownloadBase;
import com.lewa.face.download.ModelDownload;
import com.lewa.face.pojos.ThemeBase;
import com.lewa.face.preview.slide.adapter.OnlinePkgNewServerAdapter;
import com.lewa.face.preview.slide.base.OnlineSlideBase;
import com.lewa.face.util.ThemeConstants;

/**
 * 此类暂时没用
 * @author fulw
 *
 */
public class LockScreenWallPaper extends OnlineSlideBase{
    
    private static final String TAG = LockScreenWallPaper.class.getSimpleName();

   
    
    @Override
	protected void setContentView() {
		setContentView(R.layout.theme_preview_slide_no_model);
		
		findViewById(R.id.theme_check_info).setVisibility(View.GONE);
	}
    
    
    @Override
    protected PagerAdapter initAdapter() {
        // TODO Auto-generated method stub
        onlineSlideAdapter = new OnlinePkgNewServerAdapter(source,mContext,themeBase);
        return onlineSlideAdapter;
    }
    
    @Override
    protected DownloadBase getDownloadBase(ThemeBase themeBase, Context context) {
        // TODO Auto-generated method stub
        Log.e(TAG, "ModelDownload");
        return new ModelDownload(themeBase, context);
    }
    

    @Override
    protected ArrayList<String> getList() {
        /**
         * 不包含后缀名
         */
        String name = themeBase.getName();
        
        if(name == null){
            return null;
        }
        
        ArrayList<String> list = new ArrayList<String>();
        list.add(new StringBuilder().append(ThemeConstants.THEME_ONLINE_PREVIEW).append("/").append(name).append("/preview_lockscreen_0.jpg").toString());
        list.add(new StringBuilder().append(ThemeConstants.THEME_ONLINE_PREVIEW).append("/").append(name).append("/preview_launcher_0.jpg").toString());
        list.add(new StringBuilder().append(ThemeConstants.THEME_ONLINE_PREVIEW).append("/").append(name).append("/preview_icons_0.jpg").toString());
        
        return list;
    }
    
}