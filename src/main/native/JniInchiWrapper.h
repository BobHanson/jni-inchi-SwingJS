/* JniInchiWrapper.h
 * Author: Sam Adams
 *
 * Copyright (C) Sam Adams 2006
 */
#include <jni.h>
/* Header for class JniInchiWrapper */

#ifndef LIBJNIINCHI_H_
#define LIBJNIINCHI_H_
#ifdef __cplusplus
extern "C" {
#endif

#define NATIVE_LIB_VERSION_MAJOR 1
#define NATIVE_LIB_VERSION_MINOR 4


/**
 * Start new InChI library data record.  See <tt>inchi_api.h</tt> for details.
 *
 * @param numAtoms		Number of atoms in molecule.
 * @param numStereo		Number of stereo parities defined.
 * @param options		Options string.
 */
JNIEXPORT void JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiStartInput
  (JNIEnv *env, jobject, jint numAtoms, jint numStereo, jstring options);


/**
 * Add atom definition.  See <tt>inchi_api.h</tt> for details.
 *
 * @param indx		Atom number
 * @param x			x-coordinate
 * @param y			y-coordinate
 * @param z			z-coordinate
 * @param elname	Element chemical symbol
 * @param isoMass	Isotopic mass
 * @param numH		Number of implicit hydrogens
 * @param numP		Number of implicit 1H
 * @param numD		Number of implicit 2H
 * @param numT		Number of implicit 3H
 * @param radical	Radical definition
 * @param charge	Charge on atom
 */
JNIEXPORT jboolean JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiSetAtom
  (JNIEnv *env, jobject, jint indx, jdouble x, jdouble y , jdouble z, jstring elname,
   jint isoMass, jint numH, jint numP, jint numD, jint numT, jint radical,
   jint charge);


/**
 * Set atom neighbours.  See <tt>inchi_api.h</tt> for details.
 *
 * @param atNo				Atom number
 * @param nbonds			Number of bonds
 * @param neighbourArray 	Array of other atom in each bond
 * @param typeArray			Array of type of each bond
 * @param stereoArray		Array of stereo definition for each bond
 */
JNIEXPORT jboolean JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiSetAtomBonds
  (JNIEnv * env, jobject, jint atNo, jint nbonds, jintArray neighbourArray,
      jintArray typeArray, jintArray stereoArray);


/**
 * Add stereo parity definition.  See <tt>inchi_api.h</tt> for details.
 *
 * @param i			Parity Number
 * @param atC		Central atom
 * @param at0		Atom 0
 * @param at1		Atom 1
 * @param at2		Atom 2
 * @param at3		Atom 3
 * @param type		Stereo parity type
 * @param parity	Parity
 */
JNIEXPORT jboolean JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiSetStereo
  (JNIEnv *, jobject, jint i, jint atC, jint at0, jint at1, jint at2, jint at3,
      jint type, jint parity);


/**
 * Generate InChI from loaded data.  See <tt>inchi_api.h</tt> for details.
 * @return	Return status
 */
JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGenerateInchi
  (JNIEnv *, jobject);


/**
 * Generates InChI from InChI string.
 * @param inchi		InChI string
 * @param options	Options
 * @return		Return status.
 */
JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGenerateInchiFromInchi
  (JNIEnv *env, jobject, jstring inchi, jstring options);


/**
 * Fetches InChI string from InChI library.
 */
JNIEXPORT jstring JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetInchi
  (JNIEnv *env, jobject);


/**
 * Fetches AuxInfo string from InChI library.
 */
JNIEXPORT jstring JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetAuxInfo
  (JNIEnv *env, jobject);


/**
 * Fetches Message string from InChI library.
 */
JNIEXPORT jstring JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetMessage
  (JNIEnv *env, jobject);


/**
 * Fetches Log string from InChI library.
 */
JNIEXPORT jstring JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetLog
  (JNIEnv *env, jobject);


/**
 * Frees memory used by InChI library.  Must be called once InChI has
 * been generated and all data fetched.
 */
JNIEXPORT void JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiFreeInputMem
  (JNIEnv *, jobject);

/**
 * Frees memory used by InChI library.  Must be called once InChI has
 * been generated and all data fetched.
 */
JNIEXPORT void JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiFreeOutputMem
  (JNIEnv *, jobject);



/**
 * Generates structure from InChI string.
 * @param inchi		InChI string
 * @param options	Options
 * @return		Return status.
 */
JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetStruct
  (JNIEnv *env, jobject, jstring inchi, jstring options);

/**
 * Fetches number of atoms in InChI structure.
 */
JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetNumAtoms
  (JNIEnv *, jobject);

/**
 * Fetches number of stereo parities in InChI structure.
 */
JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetNumStereo
  (JNIEnv *, jobject);

/**
 * Fetches X coordinate of atom.
 * @param indx	Atom index number
 */
JNIEXPORT jdouble JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetAtomX
  (JNIEnv *, jobject, jint indx);

/**
 * Fetches Y coordinate of atom.
 * @param indx	Atom index number
 */
JNIEXPORT jdouble JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetAtomY
  (JNIEnv *, jobject, jint indx);

/**
 * Fetches Z coordinate of atom.
 * @param indx	Atom index number
 */
JNIEXPORT jdouble JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetAtomZ
  (JNIEnv *, jobject, jint indx);

/**
 * Fetches chemical element of atom.
 * @param indx	Atom index number
 */
JNIEXPORT jstring JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetAtomElement
  (JNIEnv *env, jobject, jint indx);

/**
 * Fetches isotopic mass/isotopic mass shift of atom.
 * @param indx	Atom index number
 */
JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetAtomIsotopicMass
  (JNIEnv *, jobject, jint indx);

/**
 * Fetches radical status of atom.
 * @param indx	Atom index number
 */
JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetAtomRadical
  (JNIEnv *, jobject, jint indx);

/**
 * Fetches charge on atom.
 * @param indx	Atom index number
 */
JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetAtomCharge
  (JNIEnv *, jobject, jint indx);

/**
 * Fetches number of implicit hydrogens on atom.
 * @param indx	Atom index number
 */
JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetAtomImplicitH
  (JNIEnv *, jobject, jint indx);

/**
 * Fetches number of implicit protium (1H) on atom.
 * @param indx	Atom index number
 */
JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetAtomImplicitP
  (JNIEnv *, jobject, jint indx);

/**
 * Fetches number of implicit deuterium (2H) on atom.
 * @param indx	Atom index number
 */
JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetAtomImplicitD
  (JNIEnv *, jobject, jint indx);

/**
 * Fetches number of implicit tritum (3H) on atom.
 * @param indx	Atom index number
 */
JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetAtomImplicitT
  (JNIEnv *, jobject, jint indx);

/**
 * Fetches number of bonds on atom.
 * @param indx	Atom index number
 */
JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetAtomNumBonds
  (JNIEnv *, jobject, jint indx);

/**
 * Fetches index of neighbouring atom.
 * @param indx	Atom index number
 * @param bondIndx	Bond index number
 */
JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetAtomNeighbour
  (JNIEnv *, jobject, jint indx, jint i);

/**
 * Fetches bond type (order).
 * @param indx	Atom index number
 * @param bondIndx	Bond index number
 */
JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetAtomBondType
  (JNIEnv *, jobject, jint indx, jint i);

/**
 * Fetches bond stereochemistry.
 * @param indx	Atom index number
 * @param bondIndx	Bond index number
 */
JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetAtomBondStereo
  (JNIEnv *, jobject, jint indx, jint i);


JNIEXPORT jlong JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetStructWarningFlags00
  (JNIEnv *, jobject);


JNIEXPORT jlong JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetStructWarningFlags01
  (JNIEnv *, jobject);


JNIEXPORT jlong JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetStructWarningFlags10
  (JNIEnv *, jobject);


JNIEXPORT jlong JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetStructWarningFlags11
  (JNIEnv *, jobject);

/**
 * Frees memory used by InChI library.  Must be called once structure has
 * been generated and all data fetched.
 */
JNIEXPORT void JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiFreeStructMem
  (JNIEnv *, jobject);


JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetStereoCentralAtom
  (JNIEnv *, jobject, jint indx);

JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetStereoNeighbourAtom
  (JNIEnv *, jobject, jint indx, jint i);

JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetStereoType
  (JNIEnv *, jobject, jint indx);

JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetStereoParity
  (JNIEnv *, jobject, jint indx);


JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetVersionMajor
  (JNIEnv *, jclass);

JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetVersionMinor
  (JNIEnv *, jclass);


#ifdef __cplusplus
}
#endif
#endif
