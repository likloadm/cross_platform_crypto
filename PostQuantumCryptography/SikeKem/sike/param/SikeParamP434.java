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
 * SIKE parameters for variant SIKEp434.
 *
 * @author Roman Strobl, roman.strobl@wultra.com
 */
public class SikeParamP434 implements SikeParam {

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
     * @param implementationType Implementation type.
     */
    public SikeParamP434(ImplementationType implementationType) {
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
        return (byte) 0xFF;
    }

    @Override
    public byte getMaskB() {
        return 0x01;
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
        return 7;
    }

    @Override
    public int getZeroWords() {
        return 3;
    }

    private final FpElementOpti p = new FpElementOpti(this, new long[]{
            0xFFFFFFFFFFFFFFFFL,
            0xFFFFFFFFFFFFFFFFL,
            0xFFFFFFFFFFFFFFFFL,
            0xFDC1767AE2FFFFFFL,
            0x7BC65C783158AEA3L,
            0x6CFC5FD681C52056L,
            0x0002341F27177344L
    });

    @Override
    public FpElementOpti getP() {
        return p;
    }

    private final FpElementOpti p1 = new FpElementOpti(this, new long[]{
            0x0000000000000000L,
            0x0000000000000000L,
            0x0000000000000000L,
            0xFDC1767AE3000000L,
            0x7BC65C783158AEA3L,
            0x6CFC5FD681C52056L,
            0x0002341F27177344L
    });

    @Override
    public FpElementOpti getP1() {
        return p1;
    }

    private final FpElementOpti px2 = new FpElementOpti(this, new long[]{
            0xFFFFFFFFFFFFFFFEL,
            0xFFFFFFFFFFFFFFFFL,
            0xFFFFFFFFFFFFFFFFL,
            0xFB82ECF5C5FFFFFFL,
            0xF78CB8F062B15D47L,
            0xD9F8BFAD038A40ACL,
            0x0004683E4E2EE688L
    });

    @Override
    public FpElementOpti getPx2() {
        return px2;
    }

    private final FpElementOpti pr2 = new FpElementOpti(this, new long[]{
            0x28E55B65DCD69B30L,
            0xACEC7367768798C2L,
            0xAB27973F8311688DL,
            0x175CC6AF8D6C7C0BL,
            0xABCD92BF2DDE347EL,
            0x69E16A61C7686D9AL,
            0x000025A89BCDD12AL
    });

    @Override
    public FpElementOpti getPR2() {
        return pr2;
    }

    public int[] getPowStrategy() {
        return new int[]{3, 10, 7, 5, 6, 5, 3, 8, 4, 7, 5, 6, 4, 5, 9, 6, 3, 11, 5, 5, 2, 8, 4, 7, 7, 8, 5, 6, 4, 8, 5, 2, 10, 6, 5, 4, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 1};
    }

    public int[] getMulStrategy() {
        return new int[]{2, 15, 9, 8, 14, 12, 2, 8, 5, 15, 8, 15, 6, 6, 3, 2, 0, 10, 9, 13, 1, 12, 3, 7, 1, 10, 8, 11, 2, 15, 14, 1, 11, 12, 14, 3, 11, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 0};
    }

    public int getInitialMul() {
        return 8;
    }

    /**
     * Initialize variant SIKEp434.
     */
    private void init() {
        // EA = 216, EB = 137
        int FIELD_PRIME_PARAM_EA = 216;
        int FIELD_PRIME_PARAM_EB = 137;
        this.prime = new BigInteger("2341F271773446CFC5FD681C520567BC65C783158AEA3FDC1767AE2FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF", 16);

        // A = 6, B = 1
        Fp2Element PUBLIC_PARAM_ELEMENT_A = fp2ElementFactory.generate(new BigInteger("6"));
        Fp2Element PUBLIC_PARAM_ELEMENT_B = fp2ElementFactory.one();

        Fp2Point PUBLIC_POINT_PA, PUBLIC_POINT_QA, PUBLIC_POINT_RA;
        Fp2Point PUBLIC_POINT_PB, PUBLIC_POINT_QB, PUBLIC_POINT_RB;

        if (implementationType == ImplementationType.REFERENCE) {
            // Public points PA and QA
            FpElement PUBLIC_POINT_PA_X0 = new FpElementRef(this, new BigInteger("3CCFC5E1F050030363E6920A0F7A4C6C71E63DE63A0E6475AF621995705F7C84500CB2BB61E950E19EAB8661D25C4A50ED279646CB48", 16));
            FpElement PUBLIC_POINT_PA_X1 = new FpElementRef(this, new BigInteger("1AD1C1CAE7840EDDA6D8A924520F60E573D3B9DFAC6D189941CB22326D284A8816CC4249410FE80D68047D823C97D705246F869E3EA50", 16));
            FpElement PUBLIC_POINT_PA_Y0 = new FpElementRef(this, new BigInteger("1AB066B84949582E3F66688452B9255E72A017C45B148D719D9A63CDB7BE6F48C812E33B68161D5AB3A0A36906F04A6A6957E6F4FB2E0", 16));
            FpElement PUBLIC_POINT_PA_Y1 = new FpElementRef(this, new BigInteger("FD87F67EA576CE97FF65BF9F4F7688C4C752DCE9F8BD2B36AD66E04249AAF8337C01E6E4E1A844267BA1A1887B433729E1DD90C7DD2F", 16));
            FpElement PUBLIC_POINT_QA_X0 = new FpElementRef(this, new BigInteger("C7461738340EFCF09CE388F666EB38F7F3AFD42DC0B664D9F461F31AA2EDC6B4AB71BD42F4D7C058E13F64B237EF7DDD2ABC0DEB0C6C", 16));
            FpElement PUBLIC_POINT_QA_X1 = new FpElementRef(this, new BigInteger("25DE37157F50D75D320DD0682AB4A67E471586FBC2D31AA32E6957FA2B2614C4CD40A1E27283EAAF4272AE517847197432E2D61C85F5", 16));
            FpElement PUBLIC_POINT_QA_Y0 = new FpElementRef(this, new BigInteger("1D407B70B01E4AEE172EDF491F4EF32144F03F5E054CEF9FDE5A35EFA3642A11817905ED0D4F193F31124264924A5F64EFE14B6EC97E5", 16));
            FpElement PUBLIC_POINT_QA_Y1 = new FpElementRef(this, new BigInteger("E7DEC8C32F50A4E735A839DCDB89FE0763A184C525F7B7D0EBC0E84E9D83E9AC53A572A25D19E1464B509D97272AE761657B4765B3D6", 16));
            FpElement PUBLIC_POINT_RA_X0 = new FpElementRef(this, new BigInteger("F37AB34BA0CEAD94F43CDC50DE06AD19C67CE4928346E829CB92580DA84D7C36506A2516696BBE3AEB523AD7172A6D239513C5FD2516", 16));
            FpElement PUBLIC_POINT_RA_X1 = new FpElementRef(this, new BigInteger("196CA2ED06A657E90A73543F3902C208F410895B49CF84CD89BE9ED6E4EE7E8DF90B05F3FDB8BDFE489D1B3558E987013F9806036C5AC", 16));
            // The Y points are not defined for R point, only x coordinates are used in optimized version
            FpElement PUBLIC_POINT_RA_Y0 = new FpElementRef(this, new BigInteger("0", 16));
            FpElement PUBLIC_POINT_RA_Y1 = new FpElementRef(this, new BigInteger("0", 16));
            PUBLIC_POINT_PA = new Fp2PointAffine(new Fp2ElementRef(this, PUBLIC_POINT_PA_X0, PUBLIC_POINT_PA_X1), new Fp2ElementRef(this, PUBLIC_POINT_PA_Y0, PUBLIC_POINT_PA_Y1));
            PUBLIC_POINT_QA = new Fp2PointAffine(new Fp2ElementRef(this, PUBLIC_POINT_QA_X0, PUBLIC_POINT_QA_X1), new Fp2ElementRef(this, PUBLIC_POINT_QA_Y0, PUBLIC_POINT_QA_Y1));
            PUBLIC_POINT_RA = new Fp2PointAffine(new Fp2ElementRef(this, PUBLIC_POINT_RA_X0, PUBLIC_POINT_RA_X1), new Fp2ElementRef(this, PUBLIC_POINT_RA_Y0, PUBLIC_POINT_RA_Y1));

            // Public points PB, QB and RB
            FpElement PUBLIC_POINT_PB_X0 = new FpElementRef(this, new BigInteger("8664865EA7D816F03B31E223C26D406A2C6CD0C3D667466056AAE85895EC37368BFC009DFAFCB3D97E639F65E9E45F46573B0637B7A9", 16));
            FpElement PUBLIC_POINT_PB_X1 = new FpElementRef(this, new BigInteger("0", 16));
            FpElement PUBLIC_POINT_PB_Y0 = new FpElementRef(this, new BigInteger("6AE515593E73976091978DFBD70BDA0DD6BCAEEBFDD4FB1E748DDD9ED3FDCF679726C67A3B2CC12B39805B32B612E058A4280764443B", 16));
            FpElement PUBLIC_POINT_PB_Y1 = new FpElementRef(this, new BigInteger("0", 16));
            FpElement PUBLIC_POINT_QB_X0 = new FpElementRef(this, new BigInteger("12E84D7652558E694BF84C1FBDAAF99B83B4266C32EC65B10457BCAF94C63EB063681E8B1E7398C0B241C19B9665FDB9E1406DA3D3846", 16));
            FpElement PUBLIC_POINT_QB_X1 = new FpElementRef(this, new BigInteger("0", 16));
            FpElement PUBLIC_POINT_QB_Y0 = new FpElementRef(this, new BigInteger("0", 16));
            FpElement PUBLIC_POINT_QB_Y1 = new FpElementRef(this, new BigInteger("EBAAA6C731271673BEECE467FD5ED9CC29AB564BDED7BDEAA86DD1E0FDDF399EDCC9B49C829EF53C7D7A35C3A0745D73C424FB4A5FD2", 16));
            FpElement PUBLIC_POINT_RB_X0 = new FpElementRef(this, new BigInteger("1CD28597256D4FFE7E002E87870752A8F8A64A1CC78B5A2122074783F51B4FDE90E89C48ED91A8F4A0CCBACBFA7F51A89CE518A52B76C", 16));
            FpElement PUBLIC_POINT_RB_X1 = new FpElementRef(this, new BigInteger("147073290D78DD0CC8420B1188187D1A49DBFA24F26AAD46B2D9BB547DBB6F63A760ECB0C2B20BE52FB77BD2776C3D14BCBC404736AE4", 16));
            // The Y points are not defined for R point, only x coordinates are used in optimized version
            FpElement PUBLIC_POINT_RB_Y0 = new FpElementRef(this, new BigInteger("0", 16));
            FpElement PUBLIC_POINT_RB_Y1 = new FpElementRef(this, new BigInteger("0", 16));
            PUBLIC_POINT_PB = new Fp2PointAffine(new Fp2ElementRef(this, PUBLIC_POINT_PB_X0, PUBLIC_POINT_PB_X1), new Fp2ElementRef(this, PUBLIC_POINT_PB_Y0, PUBLIC_POINT_PB_Y1));
            PUBLIC_POINT_QB = new Fp2PointAffine(new Fp2ElementRef(this, PUBLIC_POINT_QB_X0, PUBLIC_POINT_QB_X1), new Fp2ElementRef(this, PUBLIC_POINT_QB_Y0, PUBLIC_POINT_QB_Y1));
            PUBLIC_POINT_RB = new Fp2PointAffine(new Fp2ElementRef(this, PUBLIC_POINT_RB_X0, PUBLIC_POINT_RB_X1), new Fp2ElementRef(this, PUBLIC_POINT_RB_Y0, PUBLIC_POINT_RB_Y1));
        } else {
            FpElement PUBLIC_POINT_PA_X0 = new FpElementOpti(this,  new long[]{
                    0x05ADF455C5C345BFL,
                    0x91935C5CC767AC2BL,
                    0xAFE4E879951F0257L,
                    0x70E792DC89FA27B1L,
                    0xF797F526BB48C8CDL,
                    0x2181DB6131AF621FL,
                    0x00000A1C08B1ECC4L
            });
            FpElement PUBLIC_POINT_PA_X1 = new FpElementOpti(this,  new long[]{
                    0x74840EB87CDA7788L,
                    0x2971AA0ECF9F9D0BL,
                    0xCB5732BDF41715D5L,
                    0x8CD8E51F7AACFFAAL,
                    0xA7F424730D7E419FL,
                    0xD671EB919A179E8CL,
                    0x0000FFA26C5A924AL
            });
            FpElement PUBLIC_POINT_QA_X0 = new FpElementOpti(this,  new long[]{
                    0xFEC6E64588B7273BL,
                    0xD2A626D74CBBF1C6L,
                    0xF8F58F07A78098C7L,
                    0xE23941F470841B03L,
                    0x1B63EDA2045538DDL,
                    0x735CFEB0FFD49215L,
                    0x0001C4CB77542876L
            });
            FpElement PUBLIC_POINT_QA_X1 = new FpElementOpti(this,  new long[]{
                    0xADB0F733C17FFDD6L,
                    0x6AFFBD037DA0A050L,
                    0x680EC43DB144E02FL,
                    0x1E2E5D5FF524E374L,
                    0xE2DDA115260E2995L,
                    0xA6E4B552E2EDE508L,
                    0x00018ECCDDF4B53EL
            });
            FpElement PUBLIC_POINT_RA_X0 = new FpElementOpti(this,  new long[]{
                    0x01BA4DB518CD6C7DL,
                    0x2CB0251FE3CC0611L,
                    0x259B0C6949A9121BL,
                    0x60E17AC16D2F82ADL,
                    0x3AA41F1CE175D92DL,
                    0x413FBE6A9B9BC4F3L,
                    0x00022A81D8D55643L
            });
            FpElement PUBLIC_POINT_RA_X1 = new FpElementOpti(this,  new long[]{
                    0xB8ADBC70FC82E54AL,
                    0xEF9CDDB0D5FADDEDL,
                    0x5820C734C80096A0L,
                    0x7799994BAA96E0E4L,
                    0x044961599E379AF8L,
                    0xDB2B94FBF09F27E2L,
                    0x0000B87FC716C0C6L
            });
            PUBLIC_POINT_PA = new Fp2PointProjective(new Fp2ElementOpti(this, PUBLIC_POINT_PA_X0, PUBLIC_POINT_PA_X1), fp2ElementFactory.one());
            PUBLIC_POINT_QA = new Fp2PointProjective(new Fp2ElementOpti(this, PUBLIC_POINT_QA_X0, PUBLIC_POINT_QA_X1), fp2ElementFactory.one());
            PUBLIC_POINT_RA = new Fp2PointProjective(new Fp2ElementOpti(this, PUBLIC_POINT_RA_X0, PUBLIC_POINT_RA_X1), fp2ElementFactory.one());
            FpElement PUBLIC_POINT_PB_X0 = new FpElementOpti(this,  new long[]{
                    0x6E5497556EDD48A3L,
                    0x2A61B501546F1C05L,
                    0xEB919446D049887DL,
                    0x5864A4A69D450C4FL,
                    0xB883F276A6490D2BL,
                    0x22CC287022D5F5B9L,
                    0x0001BED4772E551FL
            });
            FpElement PUBLIC_POINT_PB_X1 = new FpElementOpti(this,  new long[]{
                    0x0000000000000000L,
                    0x0000000000000000L,
                    0x0000000000000000L,
                    0x0000000000000000L,
                    0x0000000000000000L,
                    0x0000000000000000L,
                    0x0000000000000000L
            });
            FpElement PUBLIC_POINT_QB_X0 = new FpElementOpti(this,  new long[]{
                    0xFAE2A3F93D8B6B8EL,
                    0x494871F51700FE1CL,
                    0xEF1A94228413C27CL,
                    0x498FF4A4AF60BD62L,
                    0xB00AD2A708267E8AL,
                    0xF4328294E017837FL,
                    0x000034080181D8AEL
            });
            FpElement PUBLIC_POINT_QB_X1 = new FpElementOpti(this,  new long[]{
                    0x0000000000000000L,
                    0x0000000000000000L,
                    0x0000000000000000L,
                    0x0000000000000000L,
                    0x0000000000000000L,
                    0x0000000000000000L,
                    0x0000000000000000L
            });
            FpElement PUBLIC_POINT_RB_X0 = new FpElementOpti(this,  new long[]{
                    0x283B34FAFEFDC8E4L,
                    0x9208F44977C3E647L,
                    0x7DEAE962816F4E9AL,
                    0x68A2BA8AA262EC9DL,
                    0x8176F112EA43F45BL,
                    0x02106D022634F504L,
                    0x00007E8A50F02E37L
            });
            FpElement PUBLIC_POINT_RB_X1 = new FpElementOpti(this,  new long[]{
                    0xB378B7C1DA22CCB1L,
                    0x6D089C99AD1D9230L,
                    0xEBE15711813E2369L,
                    0x2B35A68239D48A53L,
                    0x445F6FD138407C93L,
                    0xBEF93B29A3F6B54BL,
                    0x000173FA910377D3L
            });
            PUBLIC_POINT_PB = new Fp2PointProjective(new Fp2ElementOpti(this, PUBLIC_POINT_PB_X0, PUBLIC_POINT_PB_X1), fp2ElementFactory.one());
            PUBLIC_POINT_QB = new Fp2PointProjective(new Fp2ElementOpti(this, PUBLIC_POINT_QB_X0, PUBLIC_POINT_QB_X1), fp2ElementFactory.one());
            PUBLIC_POINT_RB = new Fp2PointProjective(new Fp2ElementOpti(this, PUBLIC_POINT_RB_X0, PUBLIC_POINT_RB_X1), fp2ElementFactory.one());
        }

        this.name = "SIKEp434";
        this.a = PUBLIC_PARAM_ELEMENT_A;
        this.b = PUBLIC_PARAM_ELEMENT_B;
        this.eA = FIELD_PRIME_PARAM_EA;
        this.eB = FIELD_PRIME_PARAM_EB;
        this.ordA = new BigInteger("105312291668557186697918027683670432318895095400549111254310977536");
        this.ordB = new BigInteger("232066203043628532565045340531182604896544238770765380550355483363");
        this.bitsA = 216;
        this.bitsB = 218;
        this.pA = PUBLIC_POINT_PA;
        this.qA = PUBLIC_POINT_QA;
        this.rA = PUBLIC_POINT_RA;
        this.pB = PUBLIC_POINT_PB;
        this.qB = PUBLIC_POINT_QB;
        this.rB = PUBLIC_POINT_RB;
        this.cryptoBytes = 16;
        this.messageBytes = 16;
        this.treeRowsA = 108;
        this.treeRowsB = 137;
        this.treePointsA = 7;
        this.treePointsB = 8;
        this.strategyA = new int[]{48, 28, 16, 8, 4, 2, 1, 1, 2, 1, 1, 4, 2, 1, 1, 2, 1, 1, 8, 4, 2, 1, 1, 2, 1, 1, 4, 2, 1, 1, 2, 1, 1, 13, 7, 4, 2, 1, 1, 2, 1, 1, 3, 2, 1, 1, 1, 1, 5, 4, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 21, 12, 7, 4, 2, 1, 1, 2, 1, 1, 3, 2, 1, 1, 1, 1, 5, 3, 2, 1, 1, 1, 1, 2, 1, 1, 1, 9, 5, 3, 2, 1, 1, 1, 1, 2, 1, 1, 1, 4, 2, 1, 1, 1, 2, 1, 1};
        this.strategyB = new int[]{66, 33, 17, 9, 5, 3, 2, 1, 1, 1, 1, 2, 1, 1, 1, 4, 2, 1, 1, 1, 2, 1, 1, 8, 4, 2, 1, 1, 1, 2, 1, 1, 4, 2, 1, 1, 2, 1, 1, 16, 8, 4, 2, 1, 1, 1, 2, 1, 1, 4, 2, 1, 1, 2, 1, 1, 8, 4, 2, 1, 1, 2, 1, 1, 4, 2, 1, 1, 2, 1, 1, 32, 16, 8, 4, 3, 1, 1, 1, 1, 2, 1, 1, 4, 2, 1, 1, 2, 1, 1, 8, 4, 2, 1, 1, 2, 1, 1, 4, 2, 1, 1, 2, 1, 1, 16, 8, 4, 2, 1, 1, 2, 1, 1, 4, 2, 1, 1, 2, 1, 1, 8, 4, 2, 1, 1, 2, 1, 1, 4, 2, 1, 1, 2, 1, 1};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SikeParamP434 that = (SikeParamP434) o;
        return implementationType == that.implementationType &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(implementationType, name);
    }
}
