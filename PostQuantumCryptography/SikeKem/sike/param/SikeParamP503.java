/*
 * Copyright 2020 Wultra s.r.o.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package sike.param;

import sike.math.api.*;
import sike.math.optimized.Fp2PointProjective;
import sike.math.optimized.IsogenyProjective;
import sike.math.optimized.MontgomeryProjective;
import sike.math.optimized.fp.Fp2ElementFactoryOpti;
import sike.math.optimized.fp.Fp2ElementOpti;
import sike.math.optimized.fp.FpElementOpti;
import sike.math.reference.Fp2PointAffine;
import sike.math.reference.IsogenyAffine;
import sike.math.reference.MontgomeryAffine;
import sike.math.reference.fp.Fp2ElementFactoryRef;
import sike.math.reference.fp.Fp2ElementRef;
import sike.math.reference.fp.FpElementRef;
import sike.model.ImplementationType;

import java.math.BigInteger;
import java.security.InvalidParameterException;
import java.util.Objects;

/**
 * SIKE parameters for variant SIKEp503.
 *
 * @author Roman Strobl, roman.strobl@wultra.com
 */
public class SikeParamP503 implements SikeParam {

    private final ImplementationType implementationType;
    private final Fp2ElementFactory fp2ElementFactory;
    private final Montgomery montgomery;
    private final Isogeny isogeny;

    private String name;

    // Basic Montgomery curve parameters
    private Fp2Element a;
    private Fp2Element b;
    private int eA;
    private int eB;
    private BigInteger ordA;
    private BigInteger ordB;
    private int bitsA;
    private int bitsB;

    // Field prime and public points
    private BigInteger prime;
    private Fp2Point pA;
    private Fp2Point qA;
    private Fp2Point rA;
    private Fp2Point pB;
    private Fp2Point qB;
    private Fp2Point rB;

    // Sizes of generated bytes
    private int cryptoBytes;
    private int messageBytes;

    // Configuration of isogeny computation optimizations
    private int treeRowsA;
    private int treeRowsB;
    private int treePointsA;
    private int treePointsB;
    private int[] strategyA;
    private int[] strategyB;

    /**
     * Constructor of SIKE parameters.
     *
     * @param implementationType Implementation type.
     */
    public SikeParamP503(ImplementationType implementationType) {
        this.implementationType = implementationType;
        if (implementationType == ImplementationType.REFERENCE) {
            fp2ElementFactory = new Fp2ElementFactoryRef(this);
            montgomery = new MontgomeryAffine();
            isogeny = new IsogenyAffine();
        } else if (implementationType == ImplementationType.OPTIMIZED) {
            fp2ElementFactory = new Fp2ElementFactoryOpti(this);
            montgomery = new MontgomeryProjective();
            isogeny = new IsogenyProjective();
        } else {
            throw new InvalidParameterException("Unsupported implementation type: " + implementationType);
        }
        init();
    }

    @Override
    public ImplementationType getImplementationType() {
        return implementationType;
    }

    @Override
    public Fp2ElementFactory getFp2ElementFactory() {
        return fp2ElementFactory;
    }

    @Override
    public Montgomery getMontgomery() {
        return montgomery;
    }

    @Override
    public Isogeny getIsogeny() {
        return isogeny;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Fp2Element getA() {
        return a;
    }

    @Override
    public Fp2Element getB() {
        return b;
    }

    @Override
    public int getEA() {
        return eA;
    }

    @Override
    public int getEB() {
        return eB;
    }

    @Override
    public BigInteger getOrdA() {
        return ordA;
    }

    @Override
    public BigInteger getOrdB() {
        return ordB;
    }

    @Override
    public int getBitsA() {
        return bitsA;
    }

    @Override
    public int getBitsB() {
        return bitsB;
    }

    @Override
    public byte getMaskA() {
        return 0x03;
    }

    @Override
    public byte getMaskB() {
        return 0x0F;
    }

    @Override
    public BigInteger getPrime() {
        return prime;
    }

    @Override
    public Fp2Point getPA() {
        return pA;
    }

    @Override
    public Fp2Point getQA() {
        return qA;
    }

    @Override
    public Fp2Point getRA() {
        return rA;
    }

    @Override
    public Fp2Point getPB() {
        return pB;
    }

    @Override
    public Fp2Point getQB() {
        return qB;
    }

    @Override
    public Fp2Point getRB() {
        return rB;
    }

    @Override
    public int getCryptoBytes() {
        return cryptoBytes;
    }

    @Override
    public int getMessageBytes() {
        return messageBytes;
    }

    @Override
    public int getTreeRowsA() {
        return treeRowsA;
    }

    @Override
    public int getTreeRowsB() {
        return treeRowsB;
    }

    @Override
    public int getTreePointsA() {
        return treePointsA;
    }

    @Override
    public int getTreePointsB() {
        return treePointsB;
    }

    @Override
    public int[] getStrategyA() {
        return strategyA;
    }

    @Override
    public int[] getStrategyB() {
        return strategyB;
    }

    @Override
    public int getFpWords() {
        return 8;
    }

    @Override
    public int getZeroWords() {
        return 3;
    }

    private final FpElementOpti p = new FpElementOpti(this, new long[]{
            0xFFFFFFFFFFFFFFFFL,
            0xFFFFFFFFFFFFFFFFL,
            0xFFFFFFFFFFFFFFFFL,
            0xABFFFFFFFFFFFFFFL,
            0x13085BDA2211E7A0L,
            0x1B9BF6C87B7E7DAFL,
            0x6045C6BDDA77A4D0L,
            0x004066F541811E1EL
    });

    @Override
    public FpElementOpti getP() {
        return p;
    }

    private final FpElementOpti p1 = new FpElementOpti(this, new long[]{
            0x0000000000000000L,
            0x0000000000000000L,
            0x0000000000000000L,
            0xAC00000000000000L,
            0x13085BDA2211E7A0L,
            0x1B9BF6C87B7E7DAFL,
            0x6045C6BDDA77A4D0L,
            0x004066F541811E1EL
    });

    @Override
    public FpElementOpti getP1() {
        return p1;
    }

    private final FpElementOpti px2 = new FpElementOpti(this, new long[]{
            0xFFFFFFFFFFFFFFFEL,
            0xFFFFFFFFFFFFFFFFL,
            0xFFFFFFFFFFFFFFFFL,
            0x57FFFFFFFFFFFFFFL,
            0x2610B7B44423CF41L,
            0x3737ED90F6FCFB5EL,
            0xC08B8D7BB4EF49A0L,
            0x0080CDEA83023C3CL
    });

    @Override
    public FpElementOpti getPx2() {
        return px2;
    }

    private final FpElementOpti pr2 = new FpElementOpti(this, new long[]{
            0x5289A0CF641D011FL,
            0x9B88257189FED2B9L,
            0xA3B365D58DC8F17AL,
            0x5BC57AB6EFF168ECL,
            0x9E51998BD84D4423L,
            0xBF8999CBAC3B5695L,
            0x46E9127BCE14CDB6L,
            0x003F6CFCE8B81771L
    });

    @Override
    public FpElementOpti getPR2() {
        return pr2;
    }

    public int[] getPowStrategy() {
        return new int[]{12, 5, 5, 2, 7, 11, 3, 8, 4, 11, 4, 7, 5, 6, 3, 7, 5, 7, 2, 12, 5, 6, 4, 6, 8, 6, 4, 7, 5, 5, 8, 5, 8, 5, 5, 8, 9, 3, 6, 2, 10, 6, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 3};
    }

    public int[] getMulStrategy() {
        return new int[]{12, 11, 10, 0, 1, 8, 3, 7, 1, 8, 3, 6, 7, 14, 2, 14, 14, 9, 0, 13, 9, 15, 5, 12, 7, 13, 7, 15, 6, 7, 9, 0, 5, 7, 6, 8, 8, 3, 7, 0, 10, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 3};
    }

    public int getInitialMul() {
        return 0;
    }

    /**
     * Initialize variant SIKEp503.
     */
    private void init() {
        // EA = 250, EB = 159
        int FIELD_PRIME_PARAM_EA = 250;
        int FIELD_PRIME_PARAM_EB = 159;
        this.prime = new BigInteger("4066F541811E1E6045C6BDDA77A4D01B9BF6C87B7E7DAF13085BDA2211E7A0ABFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF", 16);

        // A = 6, B = 1
        Fp2Element PUBLIC_PARAM_ELEMENT_A = fp2ElementFactory.generate(new BigInteger("6"));
        Fp2Element PUBLIC_PARAM_ELEMENT_B = fp2ElementFactory.one();

        Fp2Point PUBLIC_POINT_PA, PUBLIC_POINT_QA, PUBLIC_POINT_RA;
        Fp2Point PUBLIC_POINT_PB, PUBLIC_POINT_QB, PUBLIC_POINT_RB;

        if (implementationType == ImplementationType.REFERENCE) {
            // Public points PA and QA
            FpElementRef PUBLIC_POINT_PA_X0 = new FpElementRef(this, new BigInteger("2ED31A03825FA14BC1D92C503C061D843223E611A92D7C5FBEC0F2C915EE7EEE73374DF6A1161EA00CDCB786155E21FD38220C3772CE670BC68274B851678", 16));
            FpElementRef PUBLIC_POINT_PA_X1 = new FpElementRef(this, new BigInteger("1EE4E4E9448FBBAB4B5BAEF280A99B7BF86A1CE05D55BD603C3BA9D7C08FD8DE7968B49A78851FFBC6D0A17CB2FA1B57F3BABEF87720DD9A489B5581F915D2", 16));
            FpElementRef PUBLIC_POINT_PA_Y0 = new FpElementRef(this, new BigInteger("244D5F814B6253688138E317F24975E596B09BB15C6418E5295AAF73BA7F96EFED145DFAE1B21A8B7B121FEFA1B6E8B52F00478218589E604B97359B8A6E0F", 16));
            FpElementRef PUBLIC_POINT_PA_Y1 = new FpElementRef(this, new BigInteger("181CCC9F0CBE1390CC14149E8DE88EE79992DA32230DEDB25F04FADE07F242A9057366060CB59927DB6DC8B20E6B15747156E3C5300545E9674487AB393CA7", 16));
            FpElementRef PUBLIC_POINT_QA_X0 = new FpElementRef(this, new BigInteger("325CF6A8E2C6183A8B9932198039A7F965BA8587B67925D08D809DBF9A69DE1B621F7F134FA2DAB82FF5A2615F92CC71419FFFAAF86A290D604AB167616461", 16));
            FpElementRef PUBLIC_POINT_QA_X1 = new FpElementRef(this, new BigInteger("3E7B0494C8E60A8B72308AE09ED34845B34EA0911E356B77A11872CF7FEEFF745D98D0624097BC1AD7CD2ADF7FFC2C1AA5BA3C6684B964FA555A0715E57DB1", 16));
            FpElementRef PUBLIC_POINT_QA_Y0 = new FpElementRef(this, new BigInteger("3A34654000BD4CB2612017BD5A1965A9F89FE11C55D517DF91B89B94F4F9C58B9A9DD056915573FEDC09CCD4997E82378759E00A2DE225CE04589D201FD754", 16));
            FpElementRef PUBLIC_POINT_QA_Y1 = new FpElementRef(this, new BigInteger("19DEF0E8930E5123A22E346B1FFBD35EB01451647D8708A4835473B2539BD26806ED105A29F2D3F7EAA262426A965338782C5D20FBF478E4D1C8DBFC5B8294", 16));
            FpElementRef PUBLIC_POINT_RA_X0 = new FpElementRef(this, new BigInteger("3D24CF1F347F1DA54C1696442E6AFC192CEE5E320905E0EAB3C9D3FB595CA26C154F39427A0416A9F36337354CF1E6E5AEDD73DF80C710026D49550AC8CE9F", 16));
            FpElementRef PUBLIC_POINT_RA_X1 = new FpElementRef(this, new BigInteger("6869EA28E4CEE05DCEE8B08ACD59775D03DAA0DC8B094C85156C212C23C72CB2AB2D2D90D46375AA6D66E58E44F8F219431D3006FDED7993F51649C029498", 16));
            // The Y points are not defined for R point, only x coordinates are used in optimized version
            FpElementRef PUBLIC_POINT_RA_Y0 = new FpElementRef(this, new BigInteger("0", 16));
            FpElementRef PUBLIC_POINT_RA_Y1 = new FpElementRef(this, new BigInteger("0", 16));
            PUBLIC_POINT_PA = new Fp2PointAffine(new Fp2ElementRef(this, PUBLIC_POINT_PA_X0, PUBLIC_POINT_PA_X1), new Fp2ElementRef(this, PUBLIC_POINT_PA_Y0, PUBLIC_POINT_PA_Y1));
            PUBLIC_POINT_QA = new Fp2PointAffine(new Fp2ElementRef(this, PUBLIC_POINT_QA_X0, PUBLIC_POINT_QA_X1), new Fp2ElementRef(this, PUBLIC_POINT_QA_Y0, PUBLIC_POINT_QA_Y1));
            PUBLIC_POINT_RA = new Fp2PointAffine(new Fp2ElementRef(this, PUBLIC_POINT_RA_X0, PUBLIC_POINT_RA_X1), new Fp2ElementRef(this, PUBLIC_POINT_RA_Y0, PUBLIC_POINT_RA_Y1));

            // Public points PB, QB and RB
            FpElementRef PUBLIC_POINT_PB_X0 = new FpElementRef(this, new BigInteger("32D03FD1E99ED0CB05C0707AF74617CBEA5AC6B75905B4B54B1B0C2D73697840155E7B1005EFB02B5D02797A8B66A5D258C76A3C9EF745CECE11E9A178BADF", 16));
            FpElementRef PUBLIC_POINT_PB_X1 = new FpElementRef(this, new BigInteger("0", 16));
            FpElementRef PUBLIC_POINT_PB_Y0 = new FpElementRef(this, new BigInteger("2D810F828E3DC024D1BBBC7D6FA6E302CC5D458571763B7CCD0E4DBC9FA1163F0C1F8F4AE32A57F89DF8D2586D2A06E9FA30442B94A725266358C45236ADF3", 16));
            FpElementRef PUBLIC_POINT_PB_Y1 = new FpElementRef(this, new BigInteger("0", 16));
            FpElementRef PUBLIC_POINT_QB_X0 = new FpElementRef(this, new BigInteger("39014A74763076675D24CF3FA28318DAC75BCB04E54ADDC6494693F72EBB7DA7DC6A3BBCD188DAD5BECE9D6BB4ABDD05DB38C5FBE52D985DCAF74422C24D53", 16));
            FpElementRef PUBLIC_POINT_QB_X1 = new FpElementRef(this, new BigInteger("0", 16));
            FpElementRef PUBLIC_POINT_QB_Y0 = new FpElementRef(this, new BigInteger("0", 16));
            FpElementRef PUBLIC_POINT_QB_Y1 = new FpElementRef(this, new BigInteger("25512012C90A6869C4B29B9A757A03006BC7DF0BF7A2526A0713939FA48018AE3E249BD63699BEB3B8DEA215B7AE1B5A30FE371B64C5F1B0BF051A11D68E04", 16));
            FpElementRef PUBLIC_POINT_RB_X0 = new FpElementRef(this, new BigInteger("C1465FD048FFB8BF2158ED57F0CFFF0C4D5A4397C7542D722567700FDBB8B2825CAB4B725764F5F528294B7F95C17D560E25660AD3D07AB011D95B2CB522", 16));
            FpElementRef PUBLIC_POINT_RB_X1 = new FpElementRef(this, new BigInteger("288165466888BE1E78DB339034E2B8C7BDF0483BFA7AB943DFA05B2D1712317916690F5E713740E7C7D4838296E67357DC34E3460A95C330D5169721981758", 16));
            // The Y points are not defined for R point, only x coordinates are used in optimized version
            FpElementRef PUBLIC_POINT_RB_Y0 = new FpElementRef(this, new BigInteger("0", 16));
            FpElementRef PUBLIC_POINT_RB_Y1 = new FpElementRef(this, new BigInteger("0", 16));
            PUBLIC_POINT_PB = new Fp2PointAffine(new Fp2ElementRef(this, PUBLIC_POINT_PB_X0, PUBLIC_POINT_PB_X1), new Fp2ElementRef(this, PUBLIC_POINT_PB_Y0, PUBLIC_POINT_PB_Y1));
            PUBLIC_POINT_QB = new Fp2PointAffine(new Fp2ElementRef(this, PUBLIC_POINT_QB_X0, PUBLIC_POINT_QB_X1), new Fp2ElementRef(this, PUBLIC_POINT_QB_Y0, PUBLIC_POINT_QB_Y1));
            PUBLIC_POINT_RB = new Fp2PointAffine(new Fp2ElementRef(this, PUBLIC_POINT_RB_X0, PUBLIC_POINT_RB_X1), new Fp2ElementRef(this, PUBLIC_POINT_RB_Y0, PUBLIC_POINT_RB_Y1));
        } else {
            FpElement PUBLIC_POINT_PA_X0 = new FpElementOpti(this, new long[]{
                    0x5D083011589AD893L,
                    0xADFD8D2CB67D0637L,
                    0x330C9AC34FFB6361L,
                    0xF0D47489A2E805A2L,
                    0x27E2789259C6B8DCL,
                    0x63866A2C121931B9L,
                    0x8D4C65A7137DCF44L,
                    0x003A183AE5967B3FL
            });
            FpElement PUBLIC_POINT_PA_X1 = new FpElementOpti(this, new long[]{
                    0x7E3541B8C96D1519L,
                    0xD3ADAEEC0D61A26CL,
                    0xC0A2219CE7703DD9L,
                    0xFF3E46658FCDBC52L,
                    0xD5B38DEAE6E196FFL,
                    0x1AAC826364956D58L,
                    0xEC9F4875B9A5F27AL,
                    0x001B0B475AB99843L
            });
            FpElement PUBLIC_POINT_QA_X0 = new FpElementOpti(this, new long[]{
                    0x4D83695107D03BADL,
                    0x221F3299005E2FCFL,
                    0x78E6AE22F30DECF2L,
                    0x6D982DB5111253E4L,
                    0x504C80A8AB4526A8L,
                    0xEFD0C3AA210BB024L,
                    0xCB77483501DC6FCFL,
                    0x001052544A96BDF3L
            });
            FpElement PUBLIC_POINT_QA_X1 = new FpElementOpti(this, new long[]{
                    0x0D74FE3402BCAE47L,
                    0xDF5B8CDA832D8AEDL,
                    0xB86BCF06E4BD837EL,
                    0x892A2933A0FA1F63L,
                    0x9F88FC67B6CCB461L,
                    0x822926EA9DDA3AC8L,
                    0xEAC8DDE5855425EDL,
                    0x000618FE6DA37A80L
            });
            FpElement PUBLIC_POINT_RA_X0 = new FpElementOpti(this, new long[]{
                    0x1D9D32D2DC877C17L,
                    0x5517CD8F71D5B02BL,
                    0x395AFB8F6B60C117L,
                    0x3AE31AC85F9098C8L,
                    0x5F5341C198450848L,
                    0xF8C609DBEA435C6AL,
                    0xD832BC7EDC7BA5E4L,
                    0x002AD98AA6968BF5L
            });
            FpElement PUBLIC_POINT_RA_X1 = new FpElementOpti(this, new long[]{
                    0xC466CAB0F73C2E5BL,
                    0x7B1817148FB2CF9CL,
                    0x873E87C099E470A0L,
                    0xBB17AC6D17A7BAC1L,
                    0xA146FDCD0F2E2A58L,
                    0x88B311E9CEAB6201L,
                    0x37604CF5C7951757L,
                    0x0006804071C74BF9L
            });
            PUBLIC_POINT_PA = new Fp2PointProjective(new Fp2ElementOpti(this, PUBLIC_POINT_PA_X0, PUBLIC_POINT_PA_X1), fp2ElementFactory.one());
            PUBLIC_POINT_QA = new Fp2PointProjective(new Fp2ElementOpti(this, PUBLIC_POINT_QA_X0, PUBLIC_POINT_QA_X1), fp2ElementFactory.one());
            PUBLIC_POINT_RA = new Fp2PointProjective(new Fp2ElementOpti(this, PUBLIC_POINT_RA_X0, PUBLIC_POINT_RA_X1), fp2ElementFactory.one());
            FpElement PUBLIC_POINT_PB_X0 = new FpElementOpti(this, new long[]{
                    0xDF630FC5FB2468DBL,
                    0xC30C5541C102040EL,
                    0x3CDC9987B76511FCL,
                    0xF54B5A09353D0CDDL,
                    0x3ADBA8E00703C42FL,
                    0x8253F9303DDC95D0L,
                    0x62D30778763ABFD7L,
                    0x001CD00FB581CD55L
            });
            FpElement PUBLIC_POINT_PB_X1 = new FpElementOpti(this, new long[]{
                    0x0000000000000000L,
                    0x0000000000000000L,
                    0x0000000000000000L,
                    0x0000000000000000L,
                    0x0000000000000000L,
                    0x0000000000000000L,
                    0x0000000000000000L,
                    0x0000000000000000L
            });
            FpElement PUBLIC_POINT_QB_X0 = new FpElementOpti(this, new long[]{
                    0x2E3457A12B429261L,
                    0x311F94E89627DCF8L,
                    0x5B71C98FD1DB73F6L,
                    0x3671DB7DCFC21541L,
                    0xB6D1484C9FE0CF4FL,
                    0x19CD110717356E35L,
                    0xF4F9FB00AC9919DFL,
                    0x0035BC124D38A70BL
            });
            FpElement PUBLIC_POINT_QB_X1 = new FpElementOpti(this, new long[]{
                    0x0000000000000000L,
                    0x0000000000000000L,
                    0x0000000000000000L,
                    0x0000000000000000L,
                    0x0000000000000000L,
                    0x0000000000000000L,
                    0x0000000000000000L,
                    0x0000000000000000L
            });
            FpElement PUBLIC_POINT_RB_X0 = new FpElementOpti(this, new long[]{
                    0x2E08BB99413D2952L,
                    0xD3021467CD088D72L,
                    0x21017AF859752245L,
                    0x26314ED8FFD9DE5CL,
                    0x4AF43C73344B6686L,
                    0xCFA1F91149DF0993L,
                    0xF327A95365587A89L,
                    0x000DBF54E03D3906L
            });
            FpElement PUBLIC_POINT_RB_X1 = new FpElementOpti(this, new long[]{
                    0x03E03FF342F5F304L,
                    0x993D604D7B4B6E56L,
                    0x80412F4D9280E71FL,
                    0x0FFDC9EF990B3982L,
                    0xE584E64C51604931L,
                    0x1374F42AC8B0BBD7L,
                    0x07D5BC37DFA41A5FL,
                    0x00396CCFD61FD34CL
            });
            PUBLIC_POINT_PB = new Fp2PointProjective(new Fp2ElementOpti(this, PUBLIC_POINT_PB_X0, PUBLIC_POINT_PB_X1), fp2ElementFactory.one());
            PUBLIC_POINT_QB = new Fp2PointProjective(new Fp2ElementOpti(this, PUBLIC_POINT_QB_X0, PUBLIC_POINT_QB_X1), fp2ElementFactory.one());
            PUBLIC_POINT_RB = new Fp2PointProjective(new Fp2ElementOpti(this, PUBLIC_POINT_RB_X0, PUBLIC_POINT_RB_X1), fp2ElementFactory.one());
        }

        this.name = "SIKEp503";
        this.a = PUBLIC_PARAM_ELEMENT_A;
        this.b = PUBLIC_PARAM_ELEMENT_B;
        this.eA = FIELD_PRIME_PARAM_EA;
        this.eB = FIELD_PRIME_PARAM_EB;
        this.ordA = new BigInteger("1809251394333065553493296640760748560207343510400633813116524750123642650624");
        this.ordB = new BigInteger("7282483350946404208076885500996745047522350034970917293604274649554310785067");
        this.bitsA = 250;
        this.bitsB = 253;
        this.pA = PUBLIC_POINT_PA;
        this.qA = PUBLIC_POINT_QA;
        this.rA = PUBLIC_POINT_RA;
        this.pB = PUBLIC_POINT_PB;
        this.qB = PUBLIC_POINT_QB;
        this.rB = PUBLIC_POINT_RB;
        this.cryptoBytes = 24;
        this.messageBytes = 24;
        this.treeRowsA = 125;
        this.treeRowsB = 159;
        this.treePointsA = 7;
        this.treePointsB = 8;
        this.strategyA = new int[]{61, 32, 16, 8, 4, 2, 1, 1, 2, 1, 1, 4, 2, 1, 1, 2, 1, 1, 8, 4, 2, 1, 1, 2, 1, 1, 4, 2, 1, 1, 2, 1, 1, 16, 8, 4, 2, 1, 1, 2, 1, 1, 4, 2, 1, 1, 2, 1, 1, 8, 4, 2, 1, 1, 2, 1, 1, 4, 2, 1, 1, 2, 1, 1, 29, 16, 8, 4, 2, 1, 1, 2, 1, 1, 4, 2, 1, 1, 2, 1, 1, 8, 4, 2, 1, 1, 2, 1, 1, 4, 2, 1, 1, 2, 1, 1, 13, 8, 4, 2, 1, 1, 2, 1, 1, 4, 2, 1, 1, 2, 1, 1, 5, 4, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1};
        this.strategyB = new int[]{71, 38, 21, 13, 8, 4, 2, 1, 1, 2, 1, 1, 4, 2, 1, 1, 2, 1, 1, 5, 4, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 9, 5, 3, 2, 1, 1, 1, 1, 2, 1, 1, 1, 4, 2, 1, 1, 1, 2, 1, 1, 17, 9, 5, 3, 2, 1, 1, 1, 1, 2, 1, 1, 1, 4, 2, 1, 1, 1, 2, 1, 1, 8, 4, 2, 1, 1, 1, 2, 1, 1, 4, 2, 1, 1, 2, 1, 1, 33, 17, 9, 5, 3, 2, 1, 1, 1, 1, 2, 1, 1, 1, 4, 2, 1, 1, 1, 2, 1, 1, 8, 4, 2, 1, 1, 1, 2, 1, 1, 4, 2, 1, 1, 2, 1, 1, 16, 8, 4, 2, 1, 1, 1, 2, 1, 1, 4, 2, 1, 1, 2, 1, 1, 8, 4, 2, 1, 1, 2, 1, 1, 4, 2, 1, 1, 2, 1, 1};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SikeParamP503 that = (SikeParamP503) o;
        return implementationType == that.implementationType &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(implementationType, name);
    }
}