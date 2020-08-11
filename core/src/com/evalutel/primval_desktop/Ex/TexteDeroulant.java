package com.evalutel.primval_desktop.Ex;

import com.badlogic.gdx.Gdx;
import com.evalutel.primval_desktop.AnimationImageNew;
import com.evalutel.primval_desktop.Interfaces.MyDrawInterface;

import java.util.ArrayList;

public class TexteDeroulant extends AnimationImageNew implements MyDrawInterface
{

    ArrayList<String> list;
    int startPositionX, startPositionY;

    String str0, str1, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11;

    public TexteDeroulant(ArrayList instructions, int startPositionX, int startPositionY, float animationWidth, float animationHeight)
    {

        super(instructions, startPositionX, startPositionY, animationWidth, animationHeight);
//
//        str0 = "Bonjour,\n" +
//                "Je suis le professeur Metrologue, on va faire un jeu amusant qui s’appelle Badix.";
//        str1 = "Tu veux jouer ?";
//        str2 = "Voici la règle du jeu: \n";
//        str3 = " Je vois un oiseau\n";
//        str4 = "Je saisis une bille du sac et je la depose sur le plateau\n";
//        str5 = "Je vois maintenant 2 oiseaux \n";
//        str6 = " Je depose encore une bille\n";
//        str7 = "Tiens ! Encore un oiseau \n";
//        str8 = "Mince,  je crois que je me suis trompe, je clique sur Mademoiselle Validus pour savoir si c’est juste.\n";
//        str9 = "Validus: Non, non tu t’es trompée \n";
//        str10 = "Metrologue : Pour corriger mon erreur, je retire une bille de la planche puis je demande a Mademoiselle Validus./n";
//        str11 = "Validus:  Youpi ! Tu as gagne un diamant\t.\n";


//        String[] strs = { str0, str1, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11 };

//        list.addAll( Arrays.asList(strs) );



        int framesToAnimateQuantity = instructions.size();
//        animationFrames = new TextureRegion[framesToAnimateQuantity];

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();


        for (int i = 0; i < framesToAnimateQuantity; i++)
        {
            Object txtAux = instructions.get(i);

//            Texture imgAux = new Texture(txtAux);
//            TextureRegion textureRegionAux = new TextureRegion(imgAux);
//            animationFrames[i] = textureRegionAux;
//            animationFrames[i] = (TextureRegion) txtAux;
        }

//        animation = new Animation(1f / 6f, animationFrames);
    }


}


