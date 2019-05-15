package com.example.transfiles;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;

import com.example.transfiles.Wifi.WiFiDirectBroadcastReceiver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import android.support.v7.app.AppCompatActivity;


public class JoindreLobby extends AppCompatActivity {

    public ImageView imgview;
    public ImageButton btnloadimage,btnloadpdf;
    public Button btnOnOff, btnDiscover, btnSend, btnMode;
    public ListView listView;
    public TextView connexionStatus,read_msg_box;
    public EditText writeMsg;

    public WifiManager wifiManager;
    //This class provides the API for managing Wi-Fi peer-to-peer connectivity.
    public WifiP2pManager mManager;
    public WifiP2pManager.Channel mChannel;

    public BroadcastReceiver mReceiver;
    public IntentFilter mIntentFilter;

    public List<WifiP2pDevice> peers =new ArrayList<WifiP2pDevice>();
    public String[] deviceNameArray;
    public WifiP2pDevice[] deviceArray;

    static  final int MESSAGE_READ=1;
    static final int IMAGE_READ=2;
    static  final int PDF_READ=3;

    ServerClass serverClass;
    ClientClass clientClass;
    SendReceive sendReceive;

    public Uri imageUri;
    private static final int PICK_IMAGE = 100;
    String msg,itsa,pathofthefile;
    int itsaintent;
    Bitmap bitmap;
    File f;
    File targetf;
    String filepath;
    InputStream mInputStream;
    OutputStream mOutputStream;

    AutoCompleteTextView textIn;
    LinearLayout container;
    TextView reList, info;
    FileReader mFileReader;
    ArrayAdapter<String> adapter;
    private static final String[] NUMBER = new String[] {
            "One", "Two", "Three", "Four", "Five",
            "Six", "Seven", "Eight", "Nine", "Ten"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joindrelobby);

        pathofthefile = getIntent().getStringExtra("filepath");
        itsaintent = getIntent().getIntExtra("filepdf",0);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, NUMBER);

        textIn = (AutoCompleteTextView) findViewById(R.id.writeMsg);
        textIn.setAdapter(adapter);

        container = (LinearLayout) findViewById(R.id.container);
        reList = (TextView)findViewById(R.id.relist);
        reList.setMovementMethod(new ScrollingMovementMethod());
        info = (TextView)findViewById(R.id.info);
        info.setMovementMethod(new ScrollingMovementMethod());

        initialWork();
        exqListener();
        final Button btnrCompte =(Button) findViewById(R.id.btnrCompte);
        btnrCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getBaseContext(), Compte.class);
                startActivityForResult(myIntent, 0);
            }
        });
        final Button btnMode = (Button) findViewById(R.id.btnmode);
        btnMode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                hidelistbtnOnOff();
            }
        });

        btnloadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        btnloadpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDownload();
            }
        });
        if (pathofthefile!=null) {
            writeMsg.setText(pathofthefile);
        }
    }
    //Dirige vers le bon type de message
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what)
            {
                case MESSAGE_READ:
                    byte[] readBuff=(byte[]) msg.obj;
                    String tempMsg=new String(readBuff,0,msg.arg1);
                    read_msg_box.setText(tempMsg);
                    break;
                case IMAGE_READ:

                    byte[] buffer =(byte[]) msg.obj;
                    String tempMSG = new String(buffer,0,msg.arg1);
                    File f = new File(Environment.getExternalStorageDirectory() + "/"
                                    + getApplicationContext().getPackageName() +"/wifip2pshared-" + System.currentTimeMillis()
                                    +".jpg");
                    targetf = new File(tempMSG);
                    filepath = targetf.getPath();
                    try {
                        mOutputStream = new FileOutputStream(targetf);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    // bitmap = BitmapFactory.decodeFile(filepath);
                    imgview.setImageBitmap(BitmapFactory.decodeFile(targetf.getName()));
                    itsa ="";
                    break;
                case PDF_READ:
                    byte[] readbuff=(byte[]) msg.obj;
                    String tempmsg=new String(readbuff,0,msg.arg1);
                    try {
                        mFileReader = new FileReader(pathofthefile);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    read_msg_box.isClickable();
                    read_msg_box.setText("https:////docs.google.com/gview?embedded=true&url"+pathofthefile);
                    break;
            }

            return true;
        }
    });


    private  void exqListener() {
        btnOnOff.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (wifiManager.isWifiEnabled()){
                    wifiManager.setWifiEnabled(false);
                    btnOnOff.setText(getResources().getString(R.string.On));
                }
                else{
                    wifiManager.setWifiEnabled(true);
                    btnOnOff.setText(getResources().getString(R.string.Off));
                }
            }
        });

        btnDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess()
                    {
                        connexionStatus.setText(getResources().getString(R.string.DiscoveryS));
                        showlistbtnOnOff();
                    }

                    @Override
                    public void onFailure(int i) {
                        connexionStatus.setText(getResources().getString(R.string.DiscorveryF) + i);
                    }
                });
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final WifiP2pDevice device=deviceArray[i];
                WifiP2pConfig config =new WifiP2pConfig();
                config.deviceAddress=device.deviceAddress;

                mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.connected)+device.deviceName, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i) {
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.nconnected),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Client envoie au serveur
                if (itsa == "image")
                {
                    Log.i("cestuneimage","reussiclick");
                    try {
                        mInputStream = getContentResolver().openInputStream(imageUri);
                        f = new File(String.valueOf(mInputStream));
                        msg = f.getPath();
                        Log.i("cestuneimage","reussiclick"+msg);
                        sendReceive.write(msg.getBytes());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }

                else if (itsaintent == 200)
                {
                    Log.i("testpdf","entree reussi");
                    msg = writeMsg.getText().toString();
                    sendReceive.write(msg.getBytes());
                }
                else{
                    LayoutInflater layoutInflater =
                            (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.row,null);
                    AutoCompleteTextView textOut = (AutoCompleteTextView)addView.findViewById(R.id.textout);
                    textOut.setAdapter(adapter);
                    textOut.setText(textIn.getText().toString());
                    Button buttonRemove = (Button)addView.findViewById(R.id.remove);
                    final View.OnClickListener thisListener = new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            info.append("thisListener called:\t" + this + "\n");
                            info.append("Remove addView: " + addView + "\n\n");
                            ((LinearLayout)addView.getParent()).removeView(addView);

                            listAllAddView();
                        }
                    };
                    buttonRemove.setOnClickListener(thisListener);
                    container.addView(addView);

                    info.append(
                            "thisListener:\t" +thisListener + "\n"
                                    + "addView:\t" + addView + "\n\n"
                    );

                    listAllAddView();

                    msg = writeMsg.getText().toString();
                    sendReceive.write(msg.getBytes());
                }
            }
        });
    }

    private void listAllAddView(){
        reList.setText("");

        int childCount = container.getChildCount();

        for(int i=0; i<childCount; i++){
            View thisChild = container.getChildAt(i);
            reList.append(thisChild + "\n");

            AutoCompleteTextView childTextView = (AutoCompleteTextView) thisChild.findViewById(R.id.textout);
            String childTextViewValue = childTextView.getText().toString();
            reList.append("= " + childTextViewValue + "\n");
        }
    }

    private void showlistbtnOnOff()
    {
        listView.setVisibility(View.VISIBLE);
        btnOnOff.setVisibility(View.VISIBLE);
    }
    private void hidelistbtnOnOff()
    {
        listView.setVisibility(View.GONE);
        btnOnOff.setVisibility(View.GONE);
    }

    private void initialWork() {
        imgview = (ImageView) findViewById(R.id.userimg);
        btnloadimage = (ImageButton) findViewById(R.id.btnChargerImage);
        btnloadpdf = (ImageButton) findViewById(R.id.btnChargerPdf);
        btnOnOff=(Button) findViewById(R.id.onOff);
        btnMode=(Button) findViewById(R.id.btnmode);
        btnDiscover=(Button) findViewById(R.id.discover);
        btnSend=(Button) findViewById(R.id.sendButton);
        listView=(ListView) findViewById(R.id.peerListView);
        read_msg_box=(TextView) findViewById(R.id.readMsg);
        connexionStatus=(TextView) findViewById(R.id.connexionStatus);
        writeMsg = (EditText) findViewById(R.id.writeMsg);

        wifiManager= (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        mManager= (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel=mManager.initialize(this,getMainLooper(),null);

        mReceiver=new WiFiDirectBroadcastReceiver(mManager,mChannel,this);

        mIntentFilter=new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }

    public WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peerList) {
            if (!peerList.getDeviceList().equals(peers))
            {
                peers.clear();
                peers.addAll(peerList.getDeviceList());

                deviceNameArray = new String[peerList.getDeviceList().size()];
                deviceArray=new WifiP2pDevice[peerList.getDeviceList().size()];
                int index=0;

                for (WifiP2pDevice device : peerList.getDeviceList())
                {
                    deviceNameArray[index]=device.deviceName;
                    deviceArray[index]=device;
                    index++;
                }

                ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,deviceNameArray);
                listView.setAdapter(adapter);
            }

            if (peers.size()==0)
            {
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.ndevice),Toast.LENGTH_SHORT).show();
                return;
            }
        }
    };

    public WifiP2pManager.ConnectionInfoListener connectionInfoListener = new WifiP2pManager.ConnectionInfoListener() {
        @Override
        public void onConnectionInfoAvailable(WifiP2pInfo wifiP2pInfo) {
            final InetAddress groupOwnerAddress=wifiP2pInfo.groupOwnerAddress;

            if(wifiP2pInfo.groupFormed && wifiP2pInfo.isGroupOwner)
            {
                connexionStatus.setText(getResources().getString(R.string.Host));
                serverClass=new ServerClass();
                serverClass.start();
            }
            else if (wifiP2pInfo.groupFormed)
            {
                connexionStatus.setText(getResources().getString(R.string.Client));
                clientClass=new ClientClass(groupOwnerAddress);
                clientClass.start();
            }
        }
    };

    private void openDownload() {
        itsa ="pdf";
        Intent myIntent = new Intent(getBaseContext(), ListeDownload.class);
        startActivityForResult(myIntent, 0);
    }
    private void openGallery(){
        itsa= "image";
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M){
                imageUri = data.getData();
                writeMsg.setText(imageUri.getPath());
            }
            else
            {
                // in android version lower than M your method must work
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver,mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }
    ServerSocket serverSocket;
    public class  ServerClass extends Thread{
        Socket socket;


        @Override
        public void run() {
            try {
                serverSocket=new ServerSocket(8888);
                socket=serverSocket.accept();
                sendReceive=new SendReceive(socket);
                sendReceive.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Envoie du message
    private class SendReceive extends Thread
    {
        private Socket socket;
        private InputStream inputStream;
        private OutputStream outputStream;

        public  SendReceive(Socket skt)
        {
            socket = skt;
            try
            {
                if (itsa=="image")
                {
                    mInputStream = socket.getInputStream();
                    mOutputStream = socket.getOutputStream();
                }
                else{
                    inputStream = socket.getInputStream();
                    outputStream = socket.getOutputStream();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            byte[] buffer=new  byte[1024];//buffer contient le message
            int bytes;
            int len;
            while (socket!=null)
            {
                try
                {
                    if (itsa == "image")
                    {
                        bytes = mInputStream.read(buffer);
                        try{
                            ContentResolver cr = getApplicationContext().getContentResolver();
                            mInputStream = cr.openInputStream(imageUri.parse(imageUri.getPath()+".jpg"));
                            while ((len = mInputStream.read(buffer)) !=-1) {
                                mOutputStream.write(buffer,0,len);
                            }
                        }catch (FileNotFoundException e) {

                        }catch (IOException e) {

                        }
                    }
                    else {
                        bytes = inputStream.read(buffer);
                    }

                   if(itsa == "image"){
                        Log.i("cestuneimage","reussirun");
                        handler.obtainMessage(IMAGE_READ,bytes,-1,buffer).sendToTarget();
                    }
                   else if (itsaintent == 200){
                        handler.obtainMessage(PDF_READ,bytes,-1,buffer).sendToTarget();
                   }
                   else {
                        handler.obtainMessage(MESSAGE_READ,bytes,-1,buffer).sendToTarget();//-1 veut dire que l'on ne lutilise pas
                   }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        public void write(byte[] bytes)
        {
            try {
                if (itsa == "image")
                {
                    mOutputStream.write(bytes);
                }
                else{
                    outputStream.write(bytes);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public  class  ClientClass extends Thread
    {
        Socket socket;
        String hostAdd;

        public  ClientClass(InetAddress hostAddress)
        {
            hostAdd=hostAddress.getHostAddress();
            socket=new Socket();
        }

        @Override
        public void run() {
            try {
                socket.connect(new InetSocketAddress(hostAdd, 8888), 500);
                sendReceive=new SendReceive(socket);
                sendReceive.start();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

