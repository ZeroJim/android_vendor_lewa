package com.lewa.face.preview.slide.local;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;


import com.lewa.face.R;

import com.lewa.face.app.ThemeApplication;
import com.lewa.face.pojos.ThemeBase;
import com.lewa.face.preview.slide.adapter.LocalSlideAdapter;
import com.lewa.face.preview.slide.base.SlideBaseActivity;
import com.lewa.face.util.ThemeConstants;
import com.lewa.face.util.ThemeFileFilter;
import com.lewa.face.util.ThemeUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.lewa.view.PagerAdapter;
import android.view.View;
import android.widget.TextView;

public class BootAnimation extends SlideBaseActivity{
    
    private static final String TAG = BootAnimation.class.getSimpleName();
    
    private TextView mThemeApply = null;
    private TextView mThemeShare = null;
    private TextView mThemeDelete = null;
    
    private LocalSlideAdapter localSlideAdapter = null;
    
    @Override
	protected void setContentView() {
		setContentView(R.layout.theme_preview_slide_no_model);
	}
    
    @Override
    protected PagerAdapter initAdapter() {
        localSlideAdapter = new LocalSlideAdapter(source, mContext);
        return localSlideAdapter;
    }
    
    @Override
    protected void initOtherViews() {
        // TODO Auto-generated method stub
        findViewById(R.id.theme_check_info).setVisibility(View.GONE);
        
        findViewById(R.id.theme_bottom_bar_online).setVisibility(View.GONE);
        mThemeApply = (TextView) findViewById(R.id.theme_apply);
        mThemeShare = (TextView) findViewById(R.id.theme_share);
        mThemeDelete = (TextView) findViewById(R.id.theme_delete);

        mThemeApply.setOnClickListener(this);
        mThemeShare.setOnClickListener(this);
        mThemeDelete.setOnClickListener(this);
    }
    
    /**
     * look up preview pictures
     * @return
     */
    protected ArrayList<String> getList() {
        
        String pkg = themeBase.getPkg();
        
        if(pkg == null){
            return null;
        }
        
        ArrayList<String> list = new ArrayList<String>();
        File preview = new File(new StringBuilder().append(ThemeConstants.THEME_LOCAL_PREVIEW).append("/").append(ThemeUtil.getNameNoBuffix(pkg)).toString());
        File[] files = preview.listFiles(new ThemeFileFilter(ThemeConstants.PREVIEW_BOOTS, ThemeFileFilter.INDEXOF));
        
        if(files != null){
            for(File file : files){
                list.add(file.getAbsolutePath());
            }
        }
        
        return list;   
   }

   @Override
   public void onClick(View v) {
       switch (v.getId()) {
       case R.id.theme_apply:
       {
           new ApplyBootAnimationTask().execute("");
           break;
       }
       case R.id.theme_share:
       {
    	   ThemeUtil.shareByBT(themeBase,this);
           break;
       }
       case R.id.theme_delete:
       {
    	   if(ThemeConstants.DEFAULT_THEME_PKG.equals(themeBase.getPkg())){
    		   ThemeUtil.defaultThemeDialog(mContext);
    	   }else {
    		   ThemeUtil.deleteThemeDialog(this, themeBase);
    	   }
           
           break;
       }
       default:
           break;
       }
       
   }

   private class ApplyBootAnimationTask extends AsyncTask<String, Void, Boolean>{

       private ProgressDialog progressDialog;
       
       @Override
       protected void onPreExecute() {
           // TODO Auto-generated method stub
           super.onPreExecute();
           
           progressDialog = new ProgressDialog(mContext);
           progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
           progressDialog.setCancelable(false);
           progressDialog.setTitle(R.string.setting_boots_title);
           progressDialog.setMessage(mContext.getString(R.string.setting_boots_msg));
           progressDialog.show();
       }

       @Override
       protected Boolean doInBackground(String... params) {
           FileInputStream bootsSource = null;
           FileOutputStream bootsTarget = null;
           
            try {
                
                String pkg = themeBase.getPkg();
                
                String nameNoLwt = ThemeUtil.getNameNoBuffix(pkg);
                
                
                String soruce = new StringBuilder().append(ThemeConstants.THEME_MODEL_BOOTS_STYLE).append("/")
                    .append(ThemeConstants.THEME_MODEL_BOOTS)
                    .append("_").append(nameNoLwt).toString();
                
                File sourceFile = new File(soruce);
                
                if(!sourceFile.exists()){
                    return false;
                }
                
                File bootstemp = new File(ThemeConstants.THEME_FACE_BOOTS_TEMP);
                File bootsparent = bootstemp.getParentFile();
                if(!bootsparent.exists()){
                    bootsparent.mkdirs();
                    
                    ThemeUtil.changeFilePermission(bootsparent);
                }
                
                bootsSource = new FileInputStream(soruce);
                bootsTarget = new FileOutputStream(bootstemp);
                
                boolean bootssuccess = ThemeUtil.writeSourceToTarget(bootsSource, bootsTarget);
                
                ThemeUtil.changeFilePermission(bootstemp);
                
                if(!bootssuccess){
                    return false;
                }
                
                File target = new File(ThemeConstants.THEME_FACE_BOOTS);
                
                if(target.exists()){
                    FileUtils.forceDelete(target);
                }
                
                bootstemp.renameTo(target);
                
                ThemeUtil.changeFilePermission(target);
                
                boolean success = ThemeUtil.unZip(target,target.getAbsolutePath());
                
                return success;
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally{
                try {
                    if(bootsTarget != null){
                        bootsTarget.close();
                        bootsTarget = null;
                    }
                    if(bootsSource != null){
                        bootsSource.close();
                        bootsSource = null;
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
           return false;
           
       }

       @Override
       protected void onPostExecute(Boolean result) {
           // TODO Auto-generated method stub
           super.onPostExecute(result);
           
           progressDialog.dismiss();
           progressDialog = null;
           
           if(result){
               ThemeUtil.showToast(mContext, R.string.theme_set_success, true);
               
               SharedPreferences.Editor editor = mContext.getSharedPreferences("CURRENT_USING", Context.MODE_PRIVATE).edit();
               editor.putString("boots", themeBase.getPkg())
               .commit();
             
               ThemeUtil.applyThemeAndExit(mContext);
           }else{
               ThemeUtil.showToast(mContext, R.string.theme_set_fail, true);
           }
           
       }
       
       
       
   }
   
    @Override
    protected void onDestroy() {
        ThemeApplication.activities.remove(this);
        localSlideAdapter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
            String key) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected ThemeBase initThemeBase(Intent intent) {
        themeBase = (ThemeBase) intent.getSerializableExtra(ThemeConstants.THEMEBASE); 
        return themeBase;
    }

}