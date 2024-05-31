package theAya.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAya.AyaMod;
import theAya.actions.ModifyCardWindSpeedAction;
import theAya.characters.TheAya;


import static theAya.AyaMod.makeCardPath;

public class MapleDance extends AbstractDynamicCard {

    public static final String ID = AyaMod.makeID(MapleDance.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG = makeCardPath("MapleDance.png");

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAya.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int CARD_WIND_SPEED = 0;


    public MapleDance() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseCardWindSpeed = CARD_WIND_SPEED;
        this.baseMagicNumber = 8;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
            CardCrawlGame.sound.play("MapleDance.ogg");
            if(p instanceof TheAya) {
                TheAya.gainWindSpeed(baseCardWindSpeed);
        }
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            upgradeMagicNumber(2);
            initializeDescription();
        }
    }
    @Override
    public void triggerWhenDrawn() {
        addToBot((AbstractGameAction)new ModifyCardWindSpeedAction(this.uuid, this.magicNumber));
    }
    @Override
    public AbstractCard makeCopy() {
            MapleDance c = (MapleDance)super.makeCopy();
            c.baseCardWindSpeed = this.baseCardWindSpeed;
            return c;
    }
}
