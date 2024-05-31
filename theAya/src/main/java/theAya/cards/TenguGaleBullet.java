package theAya.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAya.AyaMod;
import theAya.actions.ViolentNewsAction;
import theAya.characters.TheAya;
import theAya.ui.WindSpeedDisplayUnit;

import static theAya.AyaMod.makeCardPath;

public class TenguGaleBullet extends AbstractDynamicCard {

    public static final String ID = AyaMod.makeID(TenguGaleBullet.class.getSimpleName());
    public static final String IMG = makeCardPath("TenguGaleBullet.png");
    public static final CardColor COLOR = TheAya.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;


    // /TEXT DECLARATION/


    // STAT DECLARATION
    private static final int BASE_MAGIC_NUMBER = 20;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;

    // /STAT DECLARATION/

    public TenguGaleBullet() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = BASE_MAGIC_NUMBER;
        this.baseDamage = 10;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int mul = 1;
        if(TheAya.getWindSpeed() >= magicNumber){
            TheAya.loseWindSpeed(magicNumber);
            TheAya.changeWindType(WindSpeedDisplayUnit.WindType.ATTACK);
        }
        if(TheAya.windType == WindSpeedDisplayUnit.WindType.ATTACK){
            mul = 3;
        }

        addToBot(new DamageAction(m, new DamageInfo(p, this.damage * mul, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(5);
            upgradeMagicNumber(-5);
            initializeDescription();
        }
    }

    private final Color dGlowColor = this.glowColor;
    @Override
    public void triggerOnGlowCheck() {
        if (TheAya.getWindSpeed() < magicNumber) {
            this.glowColor = dGlowColor;
        } else {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

}