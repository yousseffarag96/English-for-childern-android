package com.example.hp.ssa;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.VideoView;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Main_menu extends AppCompatActivity  {
    private  RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
    );
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private ListView p_listview;
    private boolean playing = false;
    private boolean isFullScreen = false;
    private VideoView vv;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle atoggle;
    private ActionMenuView amvMenu;
    private boolean flash = true;
    private int delay=500;
    private Switch flash_switch;
    private Switch speed;
    private  Context context;
    private String startPath;
    private int lastSelectedItem = 0;
    private int mySelectedItem = 0;
    private Spinner alpha_sp;
    private Spinner ptw_sp;
    private String alpha_sp_name="";
    private ListAdapter list_view_ABC;
    private ListAdapter list_view_abc;
    private ImageButton play;
    private ImageButton backward;
    private ImageButton forward;
    private ImageButton fullscreen;
    private Button apply_btn;
    private String sp2="";
    private String sp1="";
    private  LinearLayout video_control;
    private  Button bio;
    private Button fw;
    private Button t_l;
    private Button alpha;
    private Button ptw;
    private MediaController mediaController;
    private String[] ptw_list={"Animal","Bird","Cat","Cow","Chicken"};
    private String[] currentplay;
    private  String videoURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        verifyStoragePermissions(this);
        Toolbar mM=(Toolbar)findViewById(R.id.toolbar2);
        ContextWrapper w = new ContextWrapper(this);
        startPath = w.getFilesDir().toString();
        context=this;
        alpha_sp=(Spinner)findViewById(R.id.spinner);
        ptw_sp=(Spinner)findViewById(R.id.spinner2);
        amvMenu=(ActionMenuView)findViewById(R.id.amvMenu) ;
        amvMenu.setOnMenuItemClickListener(new ActionMenuView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return onOptionsItemSelected(menuItem);
            }
        });
        setSupportActionBar(mM);
        getSupportActionBar().setTitle(null);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        atoggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(atoggle);
        atoggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView NV=(NavigationView)findViewById(R.id.navigation);
        NV.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        Intent in1 =new Intent(Main_menu.this,Main_menu.class);
                        startActivity(in1);
                        break;
                    case R.id.nav_change_pass:
                        Intent in2 =new Intent(Main_menu.this,ChangPassword.class);
                        startActivity(in2);
                        break;
                    case R.id.nav_logout:
                        Intent in =new Intent(Main_menu.this,MainActivity.class);
                        startActivity(in);
                        break;
                }
                return true;
            }
        });
        flash_switch=(Switch)findViewById(R.id.switch5);
        speed=(Switch)findViewById(R.id.speed) ;
        flash_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(flash)
                {
                    flash = !flash;
                    flash_switch.setText("LEARN");
                }
                else
                {
                    flash = !flash;
                    flash_switch.setText("FLASH");
                }
            }
        });

        speed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(delay == 500)
                {
                    delay = 800;
                    speed.setText("SPEED LOW");
                }
                else
                {
                    delay = 500;
                    speed.setText("SPEED HIGH");
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,amvMenu.getMenu());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
            alpha=(Button)findViewById(R.id.alpha);
            ptw=(Button)findViewById(R.id.pictoword);
           fw=(Button)findViewById(R.id.freq);
           t_l=(Button)findViewById(R.id.tech);
           bio=(Button)findViewById(R.id.bio);
            mediaController=new MediaController(this);
           p_listview=(ListView)findViewById(R.id.listView2);
            vv=(VideoView)findViewById(R.id.videoView3);
            video_control=(LinearLayout)findViewById(R.id.mediacontroller);
           apply_btn=(Button)findViewById(R.id.apply);
            play=(ImageButton)findViewById(R.id.play);
            backward=(ImageButton)findViewById(R.id.backward);
            forward=(ImageButton)findViewById(R.id.forward);
            fullscreen=(ImageButton)findViewById(R.id.fullscreen);
        if(item.getItemId()==R.id.action_pre_starters){
            alpha.setBackgroundColor(Color.LTGRAY);
            ptw_sp.setVisibility(View.INVISIBLE);
            ptw.setVisibility(View.INVISIBLE);
            fw.setVisibility(View.INVISIBLE);
            bio.setVisibility(View.INVISIBLE);
            t_l.setVisibility(View.INVISIBLE);
            Toolbar sub=(Toolbar)findViewById(R.id.toolbar3);
            sub.setVisibility(View.VISIBLE);
            final String[] abc={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
             list_view_abc =new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,abc);
            String[]ABC={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
               list_view_ABC =new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ABC);
            p_listview.setAdapter(list_view_abc);
            ptw.setVisibility(View.INVISIBLE);
            bio.setVisibility(View.INVISIBLE);
            fw.setVisibility(View.INVISIBLE);
            t_l.setVisibility(View.INVISIBLE);
            alpha.setVisibility(View.VISIBLE);
            final ArrayAdapter<String>alpha_sp_adpter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Alphabet));
            alpha_sp_adpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            alpha_sp.setAdapter(alpha_sp_adpter);

            alpha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alpha.setBackgroundColor(getResources().getColor(R.color.pressed_color));
                    p_listview.setVisibility(View.INVISIBLE);
//                    vv.setVisibility(View.INVISIBLE);
                    alpha_sp.setVisibility(View.VISIBLE);
                    apply_btn.setVisibility(View.VISIBLE);
                    flash_switch.setVisibility(View.VISIBLE);
                    speed.setVisibility(View.VISIBLE);
                    videoURL="/storage/emulated/0/Encrypted/";
                    p_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                               if (alpha_sp_name=="abc") {
                                   currentplay=abc;
                                   mySelectedItem = position;
                                   vv.setVisibility(View.VISIBLE);
                                   video_control.setVisibility(View.VISIBLE);
                                   vv.setVideoURI(Uri.parse(decrypt(Alphabet_small(videoURL, position))));
                                   for(int i =0;i<abc.length;i++)
                                   {
                                       ((View) getViewByPosition(i,p_listview)).setBackgroundColor(getResources().getColor(R.color.default_color));
                                   }

                                   ((View) getViewByPosition(mySelectedItem,p_listview)).setBackgroundColor(getResources().getColor(R.color.pressed_color));
                                   playing = !playing;


                                       vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                           @Override
                                           public void onCompletion(MediaPlayer mp) {
                                               play.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                                               if(flash) {
                                                   android.os.SystemClock.sleep(delay);
                                                   play.setImageResource(R.drawable.ic_pause_black_24dp);
                                                   lastSelectedItem = mySelectedItem;
                                                   mySelectedItem = mySelectedItem + 1;
                                                   p_listview.setSelection(mySelectedItem);
                                                   for(int i =0;i<abc.length;i++)
                                                   {
                                                       ((View) getViewByPosition(i,p_listview)).setBackgroundColor(getResources().getColor(R.color.default_color));
                                                   }

                                                   ((View) getViewByPosition(0,p_listview)).setBackgroundColor(getResources().getColor(R.color.pressed_color));
                                                   vv.setVideoURI(Uri.parse(decrypt(Alphabet_small("/storage/emulated/0/Encrypted/", mySelectedItem))));
                                                   vv.start();
                                                   if (mySelectedItem > abc.length) {
                                                       vv.pause();
                                                   }
                                               }

                                           }

                                       });


                               }

                        }
                    });
                    alpha_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            switch (position)
                            {
                                case 1:
                                    alpha_sp_name="abc";
                                    break;
                                case 2:
                                    alpha_sp_name="ABC";
                                    break;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            alpha_sp_name="abc";
                        }
                    });

                    apply_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (alpha_sp_name == "abc") {
                                p_listview.setAdapter(list_view_abc);
                                p_listview.setVisibility(View.VISIBLE);
                            }
                            if (alpha_sp_name == "ABC") {
                                p_listview.setAdapter(list_view_ABC);
                                p_listview.setVisibility(View.VISIBLE);
                            }
                        }
                    });

                }
            });

        }
        if(item.getItemId()==R.id.action_starters){
            alpha.setVisibility(View.INVISIBLE);
            ptw.setBackgroundColor(Color.LTGRAY);
            bio.setBackgroundColor(Color.LTGRAY);
            t_l.setBackgroundColor(Color.LTGRAY);
            fw.setBackgroundColor(Color.LTGRAY);
            Toolbar sub=(Toolbar)findViewById(R.id.toolbar3);
            sub.setVisibility(View.VISIBLE);
            ptw.setVisibility(View.VISIBLE);
            fw.setVisibility(View.VISIBLE);
            bio.setVisibility(View.VISIBLE);
            t_l.setVisibility(View.VISIBLE);
            currentplay=ptw_list;
            videoURL="/storage/emulated/0/Encrypted/doo/";
            final ArrayAdapter<String>ptw_sp_adpter2=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.ptw_sp1));
            final ListAdapter list_ptw=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ptw_list);
            final ArrayAdapter<String>ptw_sp_adpter1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.ptw_sp2));
            ptw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ptw.setBackgroundColor(getResources().getColor(R.color.pressed_color));
                    p_listview.setVisibility(View.INVISIBLE);
                    apply_btn.setVisibility(View.VISIBLE);
                    flash_switch.setVisibility(View.VISIBLE);
                    speed.setVisibility(View.VISIBLE);
                    alpha_sp.setVisibility(View.VISIBLE);
                    ptw_sp.setVisibility(View.VISIBLE);
                    ptw_sp_adpter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    alpha_sp.setAdapter(ptw_sp_adpter1);
                    ptw_sp_adpter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    ptw_sp.setAdapter(ptw_sp_adpter2);
                    alpha_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            switch (position){
                                case 1:
                                    sp1="1";
                                    break;
                                case 2:
                                    sp1="2";
                                    break;
                                case 3:
                                    sp1="3";
                                    break;
                                case 4:
                                    sp1="4";
                                    break;
                                case 5:
                                    sp1="5";
                                    break;
                                case 6:
                                    sp1="6";
                                    break;
                                case 7:
                                    sp1="7";
                                    break;
                                case 8:
                                    sp1="8";
                                    break;
                                case 9:
                                    sp1="9";
                                    break;
                                case 10:
                                    sp1="10";
                                    break;
                                case 11:
                                    sp1="11";
                                    break;
                                case 12:
                                    sp1="12";
                                    break;
                                case 13:
                                    sp1="13";
                                    break;
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    ptw_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            switch (position){
                                case 1:
                                    sp2="P";
                                    break;
                                case 2:
                                    sp2="V";
                                    break;
                                case 3:
                                    sp2="S";
                                    break;
                                case 4:
                                    sp2="PV";
                                    break;
                            }


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    apply_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(sp1=="1"&&sp2=="P"){
                                p_listview.setAdapter(list_ptw);
                                p_listview.setVisibility(View.VISIBLE);

                            }
                            if(sp1=="1"&&sp2=="V"){

                            }
                            if(sp1=="1"&&sp2=="S"){}
                            if(sp1=="1"&&sp2=="PV"){}
                            if(sp1=="2"&&sp2=="P"){}
                            if(sp1=="2"&&sp2=="V"){}
                            if(sp1=="2"&&sp2=="S"){}
                            if(sp1=="2"&&sp2=="PV"){}
                            if(sp1=="3"&&sp2=="P"){}
                            if(sp1=="3"&&sp2=="V"){}
                            if(sp1=="3"&&sp2=="S"){}
                            if(sp1=="3"&&sp2=="PV"){}
                            if(sp1=="4"&&sp2=="P"){}
                            if(sp1=="4"&&sp2=="V"){}
                            if(sp1=="4"&&sp2=="S"){}
                            if(sp1=="4"&&sp2=="PV"){}
                            if(sp1=="5"&&sp2=="P"){}
                            if(sp1=="5"&&sp2=="V"){}
                            if(sp1=="5"&&sp2=="S"){}
                            if(sp1=="5"&&sp2=="PV"){}
                            if(sp1=="6"&&sp2=="P"){}
                            if(sp1=="6"&&sp2=="V"){}
                            if(sp1=="6"&&sp2=="S"){}
                            if(sp1=="6"&&sp2=="PV"){}
                            if(sp1=="7"&&sp2=="P"){}
                            if(sp1=="7"&&sp2=="V"){}
                            if(sp1=="7"&&sp2=="S"){}
                            if(sp1=="7"&&sp2=="PV"){}
                            if(sp1=="8"&&sp2=="P"){}
                            if(sp1=="8"&&sp2=="V"){}
                            if(sp1=="8"&&sp2=="S"){}
                            if(sp1=="8"&&sp2=="PV"){}
                            if(sp1=="9"&&sp2=="P"){}
                            if(sp1=="9"&&sp2=="V"){}
                            if(sp1=="9"&&sp2=="S"){}
                            if(sp1=="9"&&sp2=="PV"){}
                            if(sp1=="10"&&sp2=="P"){}
                            if(sp1=="10"&&sp2=="V"){}
                            if(sp1=="10"&&sp2=="S"){}
                            if(sp1=="10"&&sp2=="PV"){}
                            if(sp1=="11"&&sp2=="P"){}
                            if(sp1=="11"&&sp2=="V"){}
                            if(sp1=="11"&&sp2=="S"){}
                            if(sp1=="11"&&sp2=="PV"){}
                            if(sp1=="12"&&sp2=="P"){}
                            if(sp1=="12"&&sp2=="V"){}
                            if(sp1=="12"&&sp2=="S"){}
                            if(sp1=="12"&&sp2=="PV"){}
                            if(sp1=="13"&&sp2=="P"){}
                            if(sp1=="13"&&sp2=="V"){}
                            if(sp1=="13"&&sp2=="S"){}
                            if(sp1=="13"&&sp2=="PV"){}
                        }
                    });
                    p_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if(sp1=="1"&&sp2=="P"){
                                mySelectedItem = position;
                                vv.setVisibility(View.VISIBLE);
                                video_control.setVisibility(View.VISIBLE);
                                vv.setVideoURI(Uri.parse(decrypt(Alphabet_small(videoURL, mySelectedItem))));
                                playing = !playing;


                                vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mp) {
                                        play.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                                        if(flash) {
                                            android.os.SystemClock.sleep(delay);
                                            play.setImageResource(R.drawable.ic_pause_black_24dp);
                                            lastSelectedItem = mySelectedItem;
                                            mySelectedItem = mySelectedItem + 1;
                                            vv.setVideoURI(Uri.parse(decrypt(Alphabet_small(videoURL, mySelectedItem))));
                                            vv.start();
                                            if (mySelectedItem > 26) {
                                                vv.pause();
                                            }
                                        }

                                    }

                                });

                            }
                        }
                    });


                }
            });




        }
        if(item.getItemId()==R.id.action_Movers){
            alpha.setVisibility(View.INVISIBLE);
            ptw.setBackgroundColor(Color.LTGRAY);
            Toolbar sub=(Toolbar)findViewById(R.id.toolbar3);
            sub.setVisibility(View.VISIBLE);
            apply_btn.setVisibility(View.VISIBLE);
            flash_switch.setVisibility(View.VISIBLE);
            speed.setVisibility(View.VISIBLE);
            alpha_sp.setVisibility(View.VISIBLE);
            ptw_sp.setVisibility(View.VISIBLE);
            ptw.setVisibility(View.VISIBLE);
            fw.setVisibility(View.INVISIBLE);
            bio.setVisibility(View.INVISIBLE);
            t_l.setVisibility(View.INVISIBLE);

        }
        if(item.getItemId()==R.id.action_Flyers){
            alpha.setVisibility(View.INVISIBLE);
            ptw.setBackgroundColor(Color.LTGRAY);
            Toolbar sub=(Toolbar)findViewById(R.id.toolbar3);
            sub.setVisibility(View.VISIBLE);
            apply_btn.setVisibility(View.VISIBLE);
            flash_switch.setVisibility(View.VISIBLE);
            speed.setVisibility(View.VISIBLE);
            alpha_sp.setVisibility(View.VISIBLE);
            ptw_sp.setVisibility(View.VISIBLE);
            ptw.setVisibility(View.VISIBLE);
            fw.setVisibility(View.INVISIBLE);
            bio.setVisibility(View.INVISIBLE);
            t_l.setVisibility(View.INVISIBLE);
        }
        if(item.getItemId()==R.id.action_ket){
            alpha.setVisibility(View.INVISIBLE);
            ptw.setBackgroundColor(Color.LTGRAY);
            Toolbar sub=(Toolbar)findViewById(R.id.toolbar3);
            sub.setVisibility(View.VISIBLE);
            apply_btn.setVisibility(View.VISIBLE);
            flash_switch.setVisibility(View.VISIBLE);
            speed.setVisibility(View.VISIBLE);
            alpha_sp.setVisibility(View.VISIBLE);
            ptw_sp.setVisibility(View.VISIBLE);
            ptw.setVisibility(View.VISIBLE);
            fw.setVisibility(View.INVISIBLE);
            bio.setVisibility(View.INVISIBLE);
            t_l.setVisibility(View.INVISIBLE);
        }
        if(atoggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String Alphabet_small(String path, int i){
        File folder=new File(path);
        File[] fields =folder.listFiles();
        String [] inPath=new String[fields.length];
        for (int count = 0; count < fields.length; count++) {
            String name = fields[count].getName();
            inPath[count]=folder.getPath()+"/"+name;
        }
        return inPath[i];
    }
    public void play_btn(View v){
        if(!playing){
            play.setImageResource(R.drawable.ic_pause_black_24dp);
            vv.start();
            playing=!playing;

        }
        else {
            vv.pause();
            play.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            playing=!playing;

        }
    }
    public void forward_btn(View v){
        if(!flash) {

            mySelectedItem += 1;

            if (mySelectedItem >= currentplay.length) {
                mySelectedItem = 0;
            }
            vv.setVideoURI(Uri.parse(decrypt(Alphabet_small(videoURL, mySelectedItem))));
            vv.start();
            play.setImageResource(R.drawable.ic_pause_black_24dp);
        }
    }
    public void backward_btn(View v){
        if(!flash) {

            mySelectedItem -= 1;

            if (mySelectedItem < 0) {
                mySelectedItem = ptw_list.length - 1;
            }
            vv.setVideoURI(Uri.parse(decrypt(Alphabet_small(videoURL, mySelectedItem))));
            vv.start();
            play.setImageResource(R.drawable.ic_pause_black_24dp);
        }
    }
    public void fullscreen_btn(View v){
        if(!isFullScreen){
            alpha.setVisibility(View.INVISIBLE);
            ptw.setVisibility(View.INVISIBLE);
            fw.setVisibility(View.INVISIBLE);
            t_l.setVisibility(View.INVISIBLE);
            bio.setVisibility(View.INVISIBLE);
            alpha_sp.setVisibility(View.INVISIBLE);
            ptw_sp.setVisibility(View.INVISIBLE);
            apply_btn.setVisibility(View.INVISIBLE);
            vv.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            vv.setLayoutParams(layoutParams);
            vv.setMediaController(mediaController);

        }
    }
    public void BIO_btn(View v){
        p_listview.setVisibility(View.INVISIBLE);
        bio.setBackgroundColor(getResources().getColor(R.color.pressed_color));
        final ArrayAdapter<String>bio_sp_adpter2=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.bio_sp2));
        final ArrayAdapter<String>bio_sp_adpter1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.bio_sp1));
    }
    private View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    private String decrypt(String fileName)
    {
        File file = new File(fileName);
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();

            bytes = Cipher.decrypt(bytes,"Mk9nYNWASQeEduqNsVUvwA==");
            File outputFileRoot =  new File(startPath + "decrypt");
            if(!outputFileRoot.exists())
                outputFileRoot.mkdirs();

            String[] splitted = fileName.split("/");

            File gpxfile = new File(outputFileRoot, splitted[splitted.length-1]);


            DataOutputStream fos = new DataOutputStream(new FileOutputStream(gpxfile));

            fos.write(bytes);
            fos.flush();
            fos.close();

            return gpxfile.getAbsolutePath();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }


        return "";
    }


}
