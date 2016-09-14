package org.spike.downloader;

import org.apache.commons.lang3.StringUtils;

public class ProgressBar implements Callback {
    private final int totalSlots;
    private int noramlizer;

    public ProgressBar(int totalSlots) {
        this.totalSlots = totalSlots;
        this.noramlizer = 100 / totalSlots;
    }

    public String progress(int percentage){
        int completedSymbols = percentage / noramlizer;
        int spaces = totalSlots - completedSymbols;

        StringBuilder builder = new StringBuilder();
        builder.append("[")
                .append(StringUtils.repeat("=", completedSymbols))
                .append(StringUtils.repeat(" ", spaces))
                .append("]");
        return "\r\b " + builder.toString() + " " + percentage + "%";
    }

    @Override
    public void invoke(int percentage) {
        display(percentage);
    }


    private void display(int percentage) {
        System.out.print(progress(percentage));
    }
}
