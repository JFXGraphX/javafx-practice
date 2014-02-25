package imageEditor;

import javafx.scene.effect.Bloom;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.SepiaTone;

public class EffectBuilder
{
    public Effect getEffect(int i)
    {
        for (Effects e : Effects.values())
        {
            if (i == e.index)
            {
                return getEffect(e);
            }
        }

        return null;
    }

    public Effect getEffect(Effects effect)
    {
        Effect e = null;

        switch (effect.index())
        {
        case 1:
            e = new Bloom(0.8D);
            break;
        case 2:
            e = new ColorAdjust();
            ((ColorAdjust) e).setSaturation(-1.0D);
            break;
        case 3:
            e = new SepiaTone();
            break;
        case 4:
            InnerShadow innerShadow = new InnerShadow();

            innerShadow.setChoke(0.0D);
            innerShadow.setRadius(49.0D);
            e = innerShadow;
            break;
        case 5:
            return null;
        }

        return e;
    }

    public static enum Effects
    {
        BLOOM(0), BW(1), SEPIA(2), BORDER(3), NORMAL(4);

        private final int index;

        private Effects(int i)
        {
            this.index = i;
        }

        public int index()
        {
            return this.index;
        }
    }
}
