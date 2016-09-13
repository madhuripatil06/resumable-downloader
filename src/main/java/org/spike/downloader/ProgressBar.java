package org.spike.downloader;

import java.util.ArrayList;
import java.util.List;

public class ProgressBar implements Callback {
    private String progress = "";

    private void addInProgress(int percentageCompleted){
        String places = "";
        places = addHashes(places, percentageCompleted);
        places = addSpaces(places, percentageCompleted);
        progress = places;

    }

    private String addSpaces(String places, int completed) {
        for(int i = 0; i <= 100 - completed ; i++){
            places += " ";
        }
        return places;
    }

    private String addHashes(String places, int completed) {
        for(int i = 0 ; i < completed ; i++){
            places += "#";
        }
        return places;
    }

    @Override
    public void invoke(int status) {
        addInProgress(status);
        System.out.print("\r\b "+ progress + status + "%");
        if(status == 100)
            System.exit(0);
    }
}
