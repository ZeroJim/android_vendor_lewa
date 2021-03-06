package com.lewa.face.online;

import java.io.IOException;
import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

import com.lewa.face.adapters.online.OnlineWallPaperAdapter;
import com.lewa.face.adapters.online.ThumbnailOnlineAdapter;
import com.lewa.face.pojos.ThemeBase;
import com.lewa.face.server.intf.UrlParam;
import com.lewa.face.util.ThemeConstants;
import com.lewa.face.util.ThemeUtil;
import android.util.Log;

/**
 * 
 * @author fulw
 *
 */
public class LockScreenWallPaper extends OnlineBaseActivity{
    
    private static final String TAG = LockScreenWallPaper.class.getSimpleName();
    
    @Override
    protected Object initUrl() {
        // TODO Auto-generated method stub
    	return UrlParam.newUrlParam(UrlParam.WALLPAPER);
    }
    
    protected boolean parseExcel(InputStream inputStream) {
        Workbook book = null;
        try {  
            WorkbookSettings ws = new WorkbookSettings();
            ws.setEncoding("UTF-8"); 
            book = Workbook.getWorkbook(inputStream,ws);
            book.getNumberOfSheets();  
            Sheet sheet = book.getSheet(0); //get the first sheet 
            int totalRows = sheet.getRows();
            totalThemes = totalRows - 1;
              
            for (int i = 1; i < totalRows; ++i) {// the first row can't contain
                
                ThemeBase lockScreenWallPaper = new ThemeBase(null,ThemeConstants.LEWA,null,false);
                
                lockScreenWallPaper.setCnName(sheet.getCell(1,i).getContents());
                lockScreenWallPaper.setEnName(sheet.getCell(2,i).getContents());
                lockScreenWallPaper.setCnAuthor(sheet.getCell(3,i).getContents());
                lockScreenWallPaper.setEnAuthor(sheet.getCell(4,i).getContents());
                lockScreenWallPaper.setSize(sheet.getCell(5, i).getContents());
                lockScreenWallPaper.setPkg(sheet.getCell(7,i).getContents());
                
                themeBases.add(lockScreenWallPaper);
                
            }
            Log.i(TAG, "Total theme numbers is " + totalThemes);
            return true; 
        } catch (Exception e) {  
           e.printStackTrace();
           return false;
        }finally{
            
            try {
                if(inputStream != null){
                    inputStream.close();
                    inputStream = null;
                }
                if(book != null){
                    book.close();
                    book = null;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }  
    }
    
    @Override
    protected ThumbnailOnlineAdapter onlineAdapterInstance() {
        
        return new OnlineWallPaperAdapter(mContext, themeBases, ThemeConstants.THEME_ONLINE);
    }
 
}
