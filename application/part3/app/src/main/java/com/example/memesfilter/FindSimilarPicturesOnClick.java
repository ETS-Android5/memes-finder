package com.example.memesfilter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;


import com.example.memesfilter.calculator.ImagesCalculator;
import com.example.memesfilter.calculator.ImagesCalculatorManager;
import com.example.memesfilter.gallery.GalleryCell;
import com.example.memesfilter.gallery.ImageOnClickAdapter;
import com.example.memesfilter.gallery.RefreshedGalleryActivity;
import com.example.memesfilter.model.FindSimilarImages;

import java.util.List;

public class FindSimilarPicturesOnClick implements ImageOnClickAdapter {

    @Override
    public View.OnClickListener getImageOnClickListener(final Activity activity, final GalleryCell galleryCell) {

        final FindSimilarImages finder = new FindSimilarImages();
        ImagesCalculator matchintImagesCalculator = new ImagesCalculator() {
            @Override
            public List<GalleryCell> getImages() {
                return finder.find(ImagesCache.getInstance().bitmapsCache.get(galleryCell.getPath()), galleryCell.getTitle());
            }
        };
        final String imageCalculatorKey = ImagesCalculatorManager.getInstance().addCalculator(matchintImagesCalculator);

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent intent = new Intent(activity, RefreshedGalleryActivity.class);
                intent.putExtra("IMAGE_CALCULATOR_KEY", imageCalculatorKey);
                intent.putExtra("TITLE", galleryCell.getTitle());
                activity.startActivity(intent);
            }
        };

    }
}
