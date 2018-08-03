package sunshine.com.krikalyt.codingexercises;

import android.content.Context;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    EditText msearchboxedittext;
    TextView murldisplaytextview;
    TextView searchresult;

    TextView errormessagetextview = null;
    ProgressBar progressBar = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        msearchboxedittext = (EditText) findViewById(R.id.et_search_box);
        murldisplaytextview = (TextView) findViewById(R.id.tv_url_display);
        searchresult = (TextView) findViewById(R.id.tv_github_search_json);

        errormessagetextview = (TextView) findViewById(R.id.tv_error_message_display);
        progressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);
    }

    public void makeGithubsearchquery()
    {
        String githubquery= msearchboxedittext.getText().toString();
        URL url = NetworkUtils.buildUrl(githubquery);
        murldisplaytextview.setText(url.toString());
        String githubresult = null;
        new githubasynctask().execute(url);
    }

    public void showJsonDataView()
    {
        errormessagetextview.setVisibility(View.INVISIBLE);
        searchresult.setVisibility(View.VISIBLE);
    }

    public void showErrorMessage()
    {
        errormessagetextview.setVisibility(View.VISIBLE);
        searchresult.setVisibility(View.INVISIBLE);

    }

    public class githubasynctask extends AsyncTask<URL,Void ,String>
    {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchurl = urls[0];
            String githubsearchresult = null;
            try{
                githubsearchresult = NetworkUtils.getResponseFromHttpUrl(searchurl);
            }catch (Exception ae)
            {
                ae.getStackTrace();
            }
            return githubsearchresult;
        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.INVISIBLE);
            if(s!=null && !s.equals(""))
            {
                showJsonDataView();
                searchresult.setText(s);
            }
            else{
                showErrorMessage();
            }
        }
    }


    public boolean onCreateOptionsMenu(Menu menu)
    {
           getMenuInflater().inflate(R.menu.mainmenu,menu);
           return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuitemselected = item.getItemId();
        if(menuitemselected==R.id.item_search)
        {
            makeGithubsearchquery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


