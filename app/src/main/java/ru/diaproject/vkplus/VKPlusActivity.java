package ru.diaproject.vkplus;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import ru.diaproject.vkplus.authorization.AuthorizationActivity;
import ru.diaproject.vkplus.core.ParentActivity;
import ru.diaproject.vkplus.core.utils.Utils;
import ru.diaproject.vkplus.news.NewsActivity;
import ru.diaproject.vkplus.vkcore.VK;

public class VKPlusActivity extends ParentActivity {
    private static final int AUTHORIZATION_CODE = 1;
    private final String logName = VKPlusActivity.this.getClass().getCanonicalName();
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vkplus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initContext(Bundle savedInstanceState) {

    }

    @Override
    protected void initBackend(Bundle savedInstanceState) {

    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        if (Utils.checkInternetConnection(this))
           if (VK.SINGLETON.isAuthorization()) {
               Intent authorizationIntent = new Intent(this, AuthorizationActivity.class);
               startActivityForResult(authorizationIntent, AUTHORIZATION_CODE);
           }
           else{
               Toast.makeText(this,getResources().getString(R.string.already_authorizate), Toast.LENGTH_SHORT ).show();
               //TODO: if first time start config activity
               Intent newsIntent = new Intent(this, NewsActivity.class);
               newsIntent.putExtra("user", VK.SINGLETON.getUser());
               startActivity(newsIntent);
           }
        else { Toast.makeText(this,getResources().getString(R.string.need_internet_connection), Toast.LENGTH_SHORT ).show();
        //TODO: doing something
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AUTHORIZATION_CODE) {
            if(resultCode == RESULT_OK){
                Intent newsIntent = new Intent(this, NewsActivity.class);
                newsIntent.putExtra("user", VK.SINGLETON.getUser());
                startActivity(newsIntent);
            }
            else
            {
                if (!VK.SINGLETON.isAuthorization()) {
                    Intent authorizationIntent = new Intent(this, AuthorizationActivity.class);
                    startActivityForResult(authorizationIntent, AUTHORIZATION_CODE);
                }
            }
        }
    }
    @Override
    protected String getLogName() {
        return logName;
    }
}
