package theAya.cards;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import theAya.AyaMod;
import theAya.characters.TheAya;
import theAya.ui.WindSpeedDisplayUnit;

import static theAya.AyaMod.makeCardPath;

public class Heal extends AbstractDynamicCard {



    public static final String ID = AyaMod.makeID(Heal.class.getSimpleName());
    public static final String IMG = makeCardPath("Heal.png");




    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAya.Enums.COLOR_GRAY;

    private static final int COST = 1;

    // /STAT DECLARATION/


    public Heal() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(TheAya.getWindSpeed()>=10){
            TheAya.loseWindSpeed(10);
            TheAya.changeWindType(WindSpeedDisplayUnit.WindType.HEAL);
        }
        if(TheAya.windType == WindSpeedDisplayUnit.WindType.HEAL){
            int tempHP = TempHPField.tempHp.get(p);
            if(tempHP>0) addToBot(new HealAction(p,p,tempHP));
            TempHPField.tempHp.set(p,0);
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            initializeDescription();
        }
    }
}