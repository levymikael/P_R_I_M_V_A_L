package com.evalutel.primval_desktop;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public  class Assets {
    private  AssetManager asm = new AssetManager();
    private  String assetsPackPath = "assets.pack";
    public  TextureAtlas atlas;

    public  void load(){
        asm.load(assetsPackPath, TextureAtlas.class);
        asm.finishLoading();

        atlas = asm.get(assetsPackPath);
    }

    public  void dispose(){
        asm.dispose();
    }
}