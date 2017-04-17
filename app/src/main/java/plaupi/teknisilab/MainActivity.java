package plaupi.teknisilab;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Dialog;
import android.app.ProgressDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import plaupi.teknisilab.config.Config;

public class MainActivity extends AppCompatActivity {

    ListView listTeknisi;
    String[] mTeknisiLab;
    ProgressDialog loading;
    Button bSubmit;
    SparseBooleanArray sparseBooleanArray ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bSubmit = (Button) findViewById(R.id.bSubmit);

        getData();
    }

    private void getData() {

       loading = ProgressDialog.show(this,"Mohon Tunggu","Pengambilan data..",false,false);

        String url = Config.URL+ "administrasi_lab/teknisiLab.php"; //inisialiasai url

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //loading.dismiss();
                showJSON(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(MainActivity.this,"Tidak ada Koneksi",Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    //menampilkan username dari tabel users yang menjabat sebagai teknisi lab
    private void showJSON(String response) {
        listTeknisi = (ListView) findViewById(R.id.listTeknisi);
        try {
            JSONArray result = new JSONArray(response);
            mTeknisiLab = new String[result.length()];

            // Parsing json
            for (int i = 0; i < result.length(); i++) {
                JSONObject Data = result.getJSONObject(i);
                 mTeknisiLab[i] = Data.getString(Config.KEY_NAMA_TEKNISI);
            }

            listTeknisi.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // TODO Auto-generated method stub


                    sparseBooleanArray = listTeknisi.getCheckedItemPositions(); //

                    String ValueHolder = "" ; //

                    int i = 0 ;

                    if(listTeknisi.getCheckedItemCount() > 0){
                        bSubmit.setEnabled(true);
                    }else{
                        bSubmit.setEnabled(false);
                    }

                    while (i < sparseBooleanArray.size()) { //

                        if (sparseBooleanArray.valueAt(i)) {

                            ValueHolder += mTeknisiLab [ sparseBooleanArray.keyAt(i) ] + ",";
                        }

                        i++ ;
                    }

                    ValueHolder = ValueHolder.replaceAll("(,)*$", "");

                    Toast.makeText(MainActivity.this, "" + ValueHolder, Toast.LENGTH_LONG).show();

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (MainActivity.this,
                        android.R.layout.simple_list_item_multiple_choice,
                        android.R.id.text1, mTeknisiLab );
        listTeknisi.setAdapter(adapter);
        loading.dismiss();
    }
}