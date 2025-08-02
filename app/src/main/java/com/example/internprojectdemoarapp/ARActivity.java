package com.example.internprojectdemoarapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.core.Anchor;
import com.google.ar.core.Config;
import com.google.ar.core.Plane;
import com.google.ar.core.TrackingState;
import com.google.ar.core.Session;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.ux.ArFragment;

public class ARActivity extends AppCompatActivity {

    private ArFragment arFragment;
    private ModelRenderable cubeRenderable;
    private AnchorNode placedAnchorNode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);

        arFragment.getArSceneView().getScene().addOnUpdateListener(frameTime -> {
            Session session = arFragment.getArSceneView().getSession();
            if (session != null) {
                Config config = session.getConfig();

                // Enable Instant Placement & Depth if supported
                if (session.isDepthModeSupported(Config.DepthMode.AUTOMATIC)) {
                    config.setDepthMode(Config.DepthMode.AUTOMATIC);
                }
                config.setInstantPlacementMode(Config.InstantPlacementMode.LOCAL_Y_UP);
                session.configure(config);
            }
        });

        buildModel();

        Toast.makeText(this, "Move your phone slowly to detect the floor", Toast.LENGTH_LONG).show();

        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            if (cubeRenderable == null) return;

            // Only place on well-tracked, large horizontal planes
            if (plane.getType() == Plane.Type.HORIZONTAL_UPWARD_FACING
                    && plane.getTrackingState() == TrackingState.TRACKING
                    && plane.getExtentX() > 0.4f && plane.getExtentZ() > 0.4f
                    && plane.isPoseInPolygon(hitResult.getHitPose())) {

                // Remove old anchor
                if (placedAnchorNode != null) {
                    placedAnchorNode.getAnchor().detach();
                    arFragment.getArSceneView().getScene().removeChild(placedAnchorNode);
                }

                Anchor anchor = hitResult.createAnchor();
                placedAnchorNode = new AnchorNode(anchor);
                placedAnchorNode.setRenderable(cubeRenderable);
                placedAnchorNode.setParent(arFragment.getArSceneView().getScene());
            } else {
                Toast.makeText(this, "Try pointing to a stable flat surface", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void buildModel() {
        MaterialFactory.makeOpaqueWithColor(this, new Color(android.graphics.Color.RED))
                .thenAccept(material -> {
                    cubeRenderable = ShapeFactory.makeCube(
                            new Vector3(0.1f, 0.1f, 0.1f),
                            new Vector3(0f, 0.05f, 0f),
                            material
                    );
                })
                .exceptionally(throwable -> {
                    runOnUiThread(() -> {
                        Toast.makeText(this, "AR model failed to build: " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                    });
                    return null;
                });
    }
}
