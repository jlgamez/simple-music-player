package com.example.SimpleMusicPlayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;



import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static  final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static final String EXTRA_MESSAGE = "com.example.SimpleMusicPlayer.extra.MESAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // peticion permisos
        ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);

        // encontrar ficheros en carpeta "musica"
        File musicFolderJgh = new File(System.getenv("EXTERNAL_STORAGE") + "/music");
        ArrayList<File> musicFilesJgh = findMusicFiles(musicFolderJgh);
        Music[] musicSetJgh = new Music[musicFilesJgh.size()];

        // Creación de array de objetos tipo Music para ser usado por el Adapter personalizado
        for (int i = 0; i < musicFilesJgh.size(); i++) {
            File currentMusicJgh = musicFilesJgh.get(i);
            musicSetJgh[i] = new Music(currentMusicJgh.getName(), i, currentMusicJgh.getAbsolutePath());
        }

        // crear elemento listView y configuración del Adaptaer personalizado
        ListView listViewMusicJgh = (ListView) findViewById(R.id.musicListView);
        MusicAdapter musicAdapterJgh = new MusicAdapter(this, musicSetJgh);
        listViewMusicJgh.setAdapter(musicAdapterJgh);

        // Evento click que inicia una segunda actividad con MediaPlayer
        listViewMusicJgh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Music chosenMusicJgh = musicSetJgh[position];
                launchMusicPlayer(chosenMusicJgh.getMusicPah(), musicSetJgh);
            }
        });

    }

    private ArrayList<File> findMusicFiles(File folderPath) {
        // encontrar todos los archivos y filtrar por mp3
        ArrayList<File> filesArrayList = new ArrayList<>();
        File[] files = folderPath.listFiles();
        for (File f : files) {
            // filtrado de .mp3
           String fileExtensionJgh = getFileExtension(f);
           if (!(fileExtensionJgh.isEmpty()) && fileExtensionJgh.equals("mp3")) {
               filesArrayList.add(f);
           }
        }
        return filesArrayList;
    }

    private String getFileExtension(File f) {
        String extensionJgh = "";
        String fileNameJgh = f.getName();
        int extensionIdxJgh = fileNameJgh.lastIndexOf(".");
        if (extensionIdxJgh > 0) {
            extensionJgh = fileNameJgh.substring(extensionIdxJgh +1);
        }
        return extensionJgh;
    }

    private void launchMusicPlayer(String musicPath, Music[] musicSet) {
        Intent intentJgh = new Intent(this, MusicPlayerActivity.class);
        intentJgh.putExtra(EXTRA_MESSAGE, musicPath);
        intentJgh.putExtra("musicArray", musicSet);
        startActivity(intentJgh);
    }
}