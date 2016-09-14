package unittests.org.spike.domain;

import org.junit.Test;
import org.spike.downloader.ProgressBar;

import static org.junit.Assert.assertEquals;

public class ProgressBarTest {

    @Test public void shouldGenerateProgressBarForGivenNumberOfPercentage() {
        ProgressBar progressBar = new ProgressBar(10);
        String displayedString = progressBar.progress(12);

        assertEquals(displayedString, "\r\b " + "[=         ] 12%");
    }

    @Test public void shouldGenerateProgressBarFor0Percentage() {
        ProgressBar progressBar = new ProgressBar(10);
        String displayedString = progressBar.progress(0);
        assertEquals(displayedString, "\r\b " + "[          ] 0%");
    }


    @Test public void shouldGenerateProgressBarFor100Percentage() {
        ProgressBar progressBar = new ProgressBar(20);
        String displayedString = progressBar.progress(100);
        assertEquals(displayedString, "\r\b " +  "[====================] 100%");
    }


}
