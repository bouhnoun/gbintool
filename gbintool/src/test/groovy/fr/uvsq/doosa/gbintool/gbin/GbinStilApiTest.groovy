package fr.uvsq.doosa.gbintool.gbin

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import uk.ac.starlink.gbin.GbinTableBuilder
import uk.ac.starlink.table.StarTable
import uk.ac.starlink.table.StarTableFactory

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@RunWith(Parameterized)
class GbinStilApiTest {
    @Parameterized.Parameters(name = "{index}: file {0} has {1} objects of type {2}")
    static data() {
        [
                ["CatalogueSource_442.gbin", 1404, "gaia.cu9.archivearchitecture.core.dmimpl.CatalogueSourceImpl", "Alpha(Double)AlphaError(Double)AstrometricChi2(float[*])AstrometricCorrelations(float[*])AstrometricDeltaQ(Float)AstrometricExcessNoise(Double)AstrometricExcessNoiseSig(Double)AstrometricGoF(Float)AstrometricNObs(int[*])AstrometricNOutliers(int[*])AstrometricParamsSolved(Byte)AstrometricPrimaryFlag(Boolean)AstrometricRankDefect(Integer)AstrometricRelegationFactor(Float)AstrometricWeight(float[*])Delta(Double)DeltaError(Double)MagGConstFlag(Byte)MagGConstLevel(Float)MagGError(Double)MagGMean(Double)MagGNObs(Integer)MatchedObservations(Short)MuAlphaStar(Double)MuAlphaStarError(Double)MuDelta(Double)MuDeltaError(Double)MuR(Double)MuRerror(Double)Observed(Boolean)Parallax(Double)ParallaxError(Double)RadialVelocity(Double)RadialVelocityConstancyProbability(Double)RadialVelocityError(Double)RandomIndex(Long)RefEpoch(Double)SolutionId(Long)SourceId(Long)Superseded(Boolean)"],
                ["CatalogueSource_rds14a_N0000000.gbin", 231767, "gaia.cu9.archivearchitecture.core.dmimpl.CatalogueSourceImpl", "Alpha(Double)AlphaError(Double)AstrometricChi2(float[*])AstrometricCorrelations(float[*])AstrometricDeltaQ(Float)AstrometricExcessNoise(Double)AstrometricExcessNoiseSig(Double)AstrometricGoF(Float)AstrometricNObs(int[*])AstrometricNOutliers(int[*])AstrometricParamsSolved(Byte)AstrometricPrimaryFlag(Boolean)AstrometricRankDefect(Integer)AstrometricRelegationFactor(Float)AstrometricWeight(float[*])Delta(Double)DeltaError(Double)MagGConstFlag(Byte)MagGConstLevel(Float)MagGError(Double)MagGMean(Double)MagGNObs(Integer)MatchedObservations(Short)MuAlphaStar(Double)MuAlphaStarError(Double)MuDelta(Double)MuDeltaError(Double)MuR(Double)MuRerror(Double)Observed(Boolean)Parallax(Double)ParallaxError(Double)RadialVelocity(Double)RadialVelocityConstancyProbability(Double)RadialVelocityError(Double)RandomIndex(Long)RefEpoch(Double)SolutionId(Long)SourceId(Long)Superseded(Boolean)"],
                ["IgslSource_000-000-246.gbin", 566798, "gaia.cu1.mdb.cu3.auxdata.igsl.dmimpl.IgslSourceImpl", "Alpha(Double)AlphaEpoch(Float)AlphaError(Float)AuxEPC(Boolean)AuxGSC23(Boolean)AuxHIP(Boolean)AuxLQRF(Boolean)AuxOGLE(Boolean)AuxPPMXL(Boolean)AuxSDSS(Boolean)AuxTMASS(Boolean)AuxTYCHO(Boolean)AuxUCAC(Boolean)Classification(Boolean)Delta(Double)DeltaEpoch(Float)DeltaError(Float)EclipticLat(Float)EclipticLon(Float)GalacticLat(Float)GalacticLon(Float)MagBJ(Float)MagBJError(Float)MagG(Float)MagGError(Float)MagGrvs(Float)MagGrvsError(Float)MagRF(Float)MagRFError(Float)MuAlpha(Float)MuAlphaError(Float)MuDelta(Float)MuDeltaError(Float)SolutionId(Long)SourceClassification(Byte)SourceId(Long)SourceMagBJ(Byte)SourceMagG(Byte)SourceMagGrvs(Byte)SourceMagRF(Byte)SourceMu(Byte)SourcePosition(Byte)ToggleASC(Boolean)"],
                //["mdbcu1integratedcompletesource_rds14a.N001333.gbin", 0, "", ""], // pb de version de lib/fichier
                ["UMStellar_N010103321.gbin", 240, "gaia.cu1.mdb.cu2.um.umtypes.dmimpl.UMStellarSourceImpl", "Age(Double)AlphaFe(Double)Alpha(Double)Delta(Double)Distance(Double)MuAlpha(Double)MuDelta(Double)RadialVelocity(Double)Astrometry_SolutionId(Long)BondAlbedo(Double)ColorVminusI(Double)Eccentricity(Double)FeH(Double)FlagInteracting(Integer)GeomAlbedo(Double)HasPhotocenterMotion(Boolean)Host(Integer)Inclination(Double)Logg(Double)LongitudeAscendingNode(Double)Mass(Double)Mbol(Double)MeanAbsoluteV(Double)Nc(Integer)Nt(Integer)OpenClusterName(String)OrbitPeriod(Double)PeriastronArgument(Double)PeriastronDate(Double)Phase(Double)Ag(Double)Av(Double)MagG(Double)MagGBp(Double)MagGRp(Double)MagGRvs(Double)Rv(Double)Photometry_SolutionId(Long)Population(Integer)REnvRStar(Double)Radius(Double)SemimajorAxis(Double)SolutionId(Long)SourceExtendedId(String)SourceId(Long)SpectralType(String)Teff(Double)VariabilityAmplitude(Double)VariabilityPeriod(Double)VariabilityPhase(Double)VariabilityType(String)Vsini(Double)"]
        ]*.toArray()
    }

    String filename
    long rowCount
    String parameters
    String columnInfo

    GbinStilApiTest(filename, rowCount, parameters, columnInfo) {
        this.filename = filename
        this.rowCount = rowCount
        this.parameters = parameters
        this.columnInfo = columnInfo
    }

//    static final filename = "CatalogueSource_442.gbin"

    StarTable starTable

    @Before
    void setup() {
        ClassLoader classLoader = getClass().getClassLoader()
        URL url = classLoader.getResource filename
        Path path = Paths.get url.getPath()
        starTable = new StarTableFactory().makeStarTable Files.newInputStream(path), new GbinTableBuilder()
    }

    @Test
    void shouldHaveTheCorrectNumberOfObjects() {
        assert starTable.rowCount == rowCount
    }

    @Test
    void shouldHaveTheCorrectParameters() {
        def parameterValues = starTable.parameters.collect { it.getValue() }
        assert parameters == parameterValues.join(", ")
    }

    @Test
    void shouldHaveTheCorrectColumnInfo() {
        def tmp = ""
        for (int i = 0; i < starTable.columnCount; i++) {
            tmp += starTable.getColumnInfo(i).toString()
        }
        assert tmp == columnInfo
    }
}
