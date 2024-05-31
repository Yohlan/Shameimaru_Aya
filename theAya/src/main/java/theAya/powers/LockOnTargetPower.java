package theAya.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theAya.AyaMod;
import theAya.util.TextureLoader;

import java.util.Random;

public class LockOnTargetPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = AyaMod.makeID("LockOnTargetPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture("theAyaResources/images/powers/lock_on84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theAyaResources/images/powers/lock_on32.png");

    public LockOnTargetPower(final AbstractCreature owner, final AbstractCreature source, final int  amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new LockOnTargetPower(owner, source, amount);
    }
    @Override
    public void atStartOfTurn() { // At the start of your turn
        Random random = new Random();
        AbstractPlayer p = AbstractDungeon.player;
        int size = AbstractDungeon.getCurrRoom().monsters.monsters.size();
        if(size == 0) return;
        for (int i = 0; i < this.amount; ++i) {
            int index = random.nextInt(size);
            AbstractMonster monster = AbstractDungeon.getCurrRoom().monsters.monsters.get(index);
            if (!monster.isDeadOrEscaped()) {
                addToBot(new ApplyPowerAction(monster, p, new BlackFeatherPower(monster, p, 1), 1));
                addToBot(new ApplyPowerAction(monster, p, new TenguMarkPower(monster, p, 1), 1));
            }
        }
    }
}
