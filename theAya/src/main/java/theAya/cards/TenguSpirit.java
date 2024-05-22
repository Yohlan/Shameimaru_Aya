package theAya.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAya.AyaMod;
import theAya.characters.TheAya;
import theAya.powers.TenguSpiritPower;

import static theAya.AyaMod.makeCardPath;

public class TenguSpirit extends AbstractDynamicCard {



    public static final String ID = AyaMod.makeID(TenguSpirit.class.getSimpleName());
    public static final String IMG = makeCardPath("TenguSpirit.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION 	

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheAya.Enums.COLOR_GRAY;

    private static final int COST = 3;
    private static final int UPGRADE_COST = 2;


    // /STAT DECLARATION/


    public TenguSpirit() {

        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(BaseModCardTags.FORM);

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new TenguSpiritPower(p, p, 1), 1));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }
}
