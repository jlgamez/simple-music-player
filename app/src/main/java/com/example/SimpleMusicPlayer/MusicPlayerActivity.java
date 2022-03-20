package com.example.SimpleMusicPlayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



import java.io.File;

public class MusicPlayerActivity extends AppCompatActivity {
    TextView songTitleTextViewJgh;
    MediaPlayer mpJgh;
    ImageView playBtn;
    ImageView prevBtn;
    ImageView nextBtn;
    int currentSongPositionJgh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        songTitleTextViewJgh = (TextView) findViewById(R.id.titleTextView);
        playBtn = (ImageView) findViewById(R.id.playBtn);
        prevBtn = (ImageView) findViewById(R.id.prevBtn);
        nextBtn = (ImageView) findViewById(R.id.nextBtn);

        // obtenemos el titulo de la canción y lo renderizamos en el reproductor
        Intent intentJgh = getIntent();
        String musicPathJgh = intentJgh.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String songTitleJgh = (new File(musicPathJgh).getName());
        songTitleTextViewJgh.setText(songTitleJgh);

        // obtenemos el array completo de canciones para reproducir
        Music[] musicArrayJgh = (Music[]) intentJgh.getSerializableExtra("musicArray");

        /*
         * con el path de la musica que hemos pasado desde ActivityMain, localizamos su posicion en
         * el Array De musica que tambien ha pasado ActivityMain
         */
        for (Music mJgh : musicArrayJgh) {
            if (mJgh.getMusicPah().equals(musicPathJgh)) {
                currentSongPositionJgh = mJgh.getId();
            }
        }


        // Inicialización y preparacion del Media Player
        mpJgh = MediaPlayer.create(this, Uri.parse(musicPathJgh));
        playOrPause();


        // configuración de click en el boton de play
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playOrPause();
            }
        });

        // configuración de click en el boton de next
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextOrPreviousSong(true, musicArrayJgh);
            }
        });

        // configuración click en el boton de previous
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextOrPreviousSong(false, musicArrayJgh);
            }
        });


    }
    private void playOrPause() {
        try {
            // reproducimos la musica si el MediaPlayer no esta ya funcionando.
            if (!(mpJgh.isPlaying())) {
                mpJgh.start();
                playBtn.setImageResource(R.drawable.ic_pause_button);
            } else {
                mpJgh.pause();
                playBtn.setImageResource(R.drawable.ic_play_button);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void nextOrPreviousSong(Boolean next, Music[] musicArray) {
        try {
            String newSongPathJgh;

            // determinar si el media Player está en marcha
            Boolean wasPlayingJgh = mpJgh.isPlaying();

            // parar y resetear el media player
            mpJgh.stop();
            mpJgh.reset();

            if (next) { // preparar canción siguiente si se habia pulsado next
                if (isLastSong(musicArray)) { // si la cancion actual era la ultima, preparar la primera cancion
                    newSongPathJgh = musicArray[0].getMusicPah();
                    // actualizar la posicion de la cancion actual en el array de musica
                    currentSongPositionJgh = 0;
                } else { // si la cancion actual no es la ultima preparar la siguiente
                    newSongPathJgh = musicArray[currentSongPositionJgh + 1].getMusicPah();
                    // actualizar la posicion de la cancion actual en el array de musica
                    currentSongPositionJgh++;
                }

            } else { // preparar canción anterior si se habia pulsado previous
                if (currentSongPositionJgh == 0) { // si la cancion actual era la primera, preparar la ultima cancion
                    newSongPathJgh = musicArray[musicArray.length - 1].getMusicPah();
                    // actualizar la posicion de la cancion actual en el array de musica
                    currentSongPositionJgh = musicArray.length - 1;
                } else { // si la cancion actual no es la primera preparar la anterior
                    newSongPathJgh = musicArray[currentSongPositionJgh - 1].getMusicPah();
                    // actualizar la posicion de la cancion actual en el array de musica
                    currentSongPositionJgh--;
                }
            }

            // actualizar la URI y preparar el media player
            mpJgh.setDataSource(this, Uri.parse(newSongPathJgh));
            mpJgh.prepare();
            // Actualizar el titulo de la cancion en el reproductor
            songTitleTextViewJgh.setText(new File(newSongPathJgh).getName());
            // reproducir nueva canción si el mediaplayer estaba en marcha
            if (wasPlayingJgh) {
                mpJgh.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Boolean isLastSong(Music[] musicArray) {
        if (currentSongPositionJgh == (musicArray.length -1)) {
            return true;
        } else {
            return false;
        }
    }

    /*
    implementamos los metodos onBackpressed y onDestroy para dejar de reproducir musica
    al volver a MainActivity
     */

    @Override
    public void onBackPressed() {
        releaseMediaPlayer();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        if (mpJgh != null) {
            if (mpJgh.isPlaying()) {
                mpJgh.stop();
            }
            mpJgh.release();
            mpJgh = null;
        }
    }


}