package theAya.ui;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theAya.AyaMod;
import theAya.characters.TheAya;


public class WindSpeedDisplayUnit {

    public int windSpeed = 0;
    public String windSpeedString = "0";
    private static Texture panel = null;
    private static Texture panel2_Heal = null;
    private static Texture panel2_Attack = null;
    private static Texture panel2_Defend = null;
    private static Texture panel2_Soar = null;
    private static Texture panel2 = null;
    private boolean isInCombat = false;
    private static boolean showing = false;
    private static final float x = 30.0F * Settings.scale;
    private static final float x2 = 120.0F * Settings.scale;
    public static final String ID = AyaMod.makeID("WindSpeedDisplayUnit");
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);

    private static final float y = 180.0F * Settings.scale;
    private static final float y2 = 255.0F * Settings.scale;
    public static final String[] TEXT = uiStrings.TEXT;
    private final Hitbox hb;
    private final Hitbox hb2;
    private static int TEXT_ref1 = 2, TEXT_ref2 = 3;

    public static enum WindType {
        SOAR,
        HEAL,
        ATTACK,
        DEFEND;
    }

    public WindSpeedDisplayUnit() {
        this.hb = new Hitbox(80.0F * Settings.scale, 80.0F * Settings.scale);
        this.hb2 = new Hitbox(50.0F * Settings.scale, 50.0F * Settings.scale);
        this.hb.x = x;
        this.hb.y = y;
        this.hb2.x = x2;
        this.hb2.y = y2;
        loadImage();
    }
    private static void loadImage() {
        if (panel == null)
            panel = ImageMaster.loadImage("theAyaResources/images/ui/WindSpeedDisplayUnit.png");
        if (panel2_Heal == null)
            panel2_Heal = ImageMaster.loadImage("theAyaResources/images/ui/Healing.png");
        if (panel2_Attack == null)
            panel2_Attack = ImageMaster.loadImage("theAyaResources/images/ui/Warfare.png");
        if (panel2_Defend == null)
            panel2_Defend = ImageMaster.loadImage("theAyaResources/images/ui/Resistance.png");
        if (panel2_Soar == null)
            panel2_Soar = ImageMaster.loadImage("theAyaResources/images/ui/Soaring.png");
        panel2 = panel2_Soar;
    }
    public void update() {
        isInCombat = AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
        if(!isInCombat) {
            return;
        }
        windSpeed = TheAya.getWindSpeed();
        windSpeedString = Integer.toString(windSpeed);
        this.hb.update();
        this.hb2.update();
        if (this.hb.hovered) {
            TipHelper.renderGenericTip(InputHelper.mX + 50.0F * Settings.scale, InputHelper.mY, TEXT[0], TEXT[1]);
        }
        if (this.hb2.hovered) {
            TipHelper.renderGenericTip(InputHelper.mX + 50.0F * Settings.scale, InputHelper.mY, TEXT[TEXT_ref1], TEXT[TEXT_ref2]);
        }
    }
    public void render(SpriteBatch sb) {
        if(!showing) {
            return;
        }
        if (!isInCombat) {
           return;
        }
        sb.setColor(Color.WHITE);
        sb.draw(panel2, this.hb2.x, this.hb2.y, 0.0F, 0.0F, 65F, 65.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
        sb.draw(panel, this.hb.x, this.hb.y, 0.0F, 0.0F, 100F, 100.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);

        FontHelper.renderFontCentered(sb, FontHelper.topPanelAmountFont, windSpeedString, x+hb.width*0.65f, y+hb.height*0.4f, Settings.CREAM_COLOR);
        this.hb.render(sb);
        this.hb2.render(sb);
    }
    public static void show() {
        showing = true;
    }
    public static void hide() {
        showing = false;
    }
    public static void changeWindType(WindType type) {
        switch (TheAya.windType){
            case SOAR:
                panel2 = panel2_Soar;
                TEXT_ref1 = 2;
                TEXT_ref2 = 3;
                break;
            case ATTACK:
                panel2 = panel2_Attack;
                TEXT_ref1 = 4;
                TEXT_ref2 = 5;
                break;
            case DEFEND:
                panel2 = panel2_Defend;
                TEXT_ref1 = 6;
                TEXT_ref2 = 7;
                break;
            case HEAL:
                panel2 = panel2_Heal;
                TEXT_ref1 = 8;
                TEXT_ref2 = 9;
                break;
        }
    }
}
