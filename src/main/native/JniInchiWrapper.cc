/*
 * JNI InChI Wrapper - A Java Native Interface Wrapper for InChI.
 * Copyright (C) 2006-2007  Sam Adams
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA
 */
#include <string>
#include <iostream>

#include "net_sf_jniinchi_JniInchiWrapper.h"
#include <inchi_api.h>

#define NATIVE_LIB_VERSION "1.5"

using namespace std;

// InChI DLL input data structure
inchi_Input  inchi_inp, *pInp = &inchi_inp;

// InChI DLL output data structure
inchi_Output inchi_out, *pOut = &inchi_out;

// InChI DLL data structure for InChI->Structure input
inchi_InputINCHI inpInChI, *pInpInchi = &inpInChI;

// InChI DLL data structure for InChI->Structure output
inchi_OutputStruct outStruct, *pOutStruct = &outStruct;

// Number of atoms
int numberAtoms;

// Number of stereo
int numberStereo;

// InChI DLL atom data structure
inchi_Atom * atoms;

// InChI DLL stereo parity data structure
inchi_Stereo0D * stereo;

// InChI DLL options string
char * optionString;

char * szMessage;
char * szLog;


/**
 * Start new InChI library data record.  See <tt>inchi_api.h</tt> for details.
 *
 * @param numAtoms      Number of atoms in molecule.
 * @param numStereo     Number of stereo parities defined.
 * @param options       Options string.
 */
JNIEXPORT void JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiStartInput
  (JNIEnv *env, jobject, jint numAtoms, jint numStereo, jstring options) {

      // Set number of atoms and stereo parities
      pInp->num_atoms = numAtoms;
      pInp->num_stereo0D = numStereo;

      numberAtoms = numAtoms;
      numberStereo = numStereo;

      // Record options
      int len = env->GetStringLength(options);
      const char *nativeString = env->GetStringUTFChars(options, 0);
      optionString = new char[len + 1];
      strcpy(optionString, nativeString);
      optionString[len] = 0;
      env->ReleaseStringUTFChars(options, nativeString);

      pInp->szOptions = optionString;

      // Get memory for atoms and stereo parities
      atoms = new inchi_Atom[numAtoms];
      stereo = new inchi_Stereo0D[numStereo];

      // Initialise atoms
      for (int i = 0; i < numAtoms; i ++) {
          atoms[i] = * new inchi_Atom();
      }

      // Initialise stereo parities
      for (int i = 0; i < numStereo; i ++) {
          stereo[i] = * new inchi_Stereo0D();
      }

      // Add atom and stereo parity array to InChI input data
      pInp->atom = atoms;
      pInp->stereo0D = stereo;
  }


/**
 * Add atom definition.  See <tt>inchi_api.h</tt> for details.
 *
 * @param indx      Atom number
 * @param x         x-coordinate
 * @param y         y-coordinate
 * @param z         z-coordinate
 * @param elname    Element chemical symbol
 * @param isoMass   Isotopic mass
 * @param numH      Number of implicit hydrogens
 * @param numP      Number of implicit 1H
 * @param numD      Number of implicit 2H
 * @param numT      Number of implicit 3H
 * @param radical   Radical definition
 * @param charge    Charge on atom
 */
JNIEXPORT jboolean JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiSetAtom
  (JNIEnv *env, jobject, jint indx, jdouble x, jdouble y , jdouble z, jstring elname,
   jint isoMass, jint numH, jint numP, jint numD, jint numT, jint radical,
   jint charge) {

      if (indx > -1 && indx < numberAtoms) {
          // Set atom coordinates
          atoms[indx].x = x;
          atoms[indx].y = y;
          atoms[indx].z = z;

          // Set element
          const char *nativeString = env->GetStringUTFChars(elname, 0);
          strcpy(atoms[indx].elname, nativeString);
          env->ReleaseStringUTFChars(elname, nativeString);

          // Set implict hydrogens (and isotopes)
          atoms[indx].num_iso_H[0] = numH;
          atoms[indx].num_iso_H[1] = numP;
          atoms[indx].num_iso_H[2] = numD;
          atoms[indx].num_iso_H[3] = numT;

          // Set spin multiplicity
          atoms[indx].radical = radical;

          // Set charge
          atoms[indx].charge = charge;

          // Set isotopic mass
          atoms[indx].isotopic_mass = isoMass;

          return(true);
      } else {
          return(false);
      }
  }


/**
 * Set atom neighbours.  See <tt>inchi_api.h</tt> for details.
 *
 * @param atNo              Atom number
 * @param nbonds            Number of bonds
 * @param neighbourArray    Array of other atom in each bond
 * @param typeArray         Array of type of each bond
 * @param stereoArray       Array of stereo definition for each bond
 */
JNIEXPORT jboolean JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiSetAtomBonds
  (JNIEnv * env, jobject, jint atNo, jint nbonds, jintArray neighbourArray,
      jintArray typeArray, jintArray stereoArray) {

      if (atNo > -1 && atNo < numberAtoms) {

          // Create c++ arrays
          jint * neighbours = env->GetIntArrayElements(neighbourArray, 0);
          jint * types = env->GetIntArrayElements(typeArray, 0);
          jint * stereo = env->GetIntArrayElements(stereoArray, 0);

          // Record atom neighbours
          atoms[atNo].num_bonds = nbonds;
          for (int bondNo = 0; bondNo < nbonds; bondNo ++) {
              atoms[atNo].neighbor[bondNo] = neighbours[bondNo];
              atoms[atNo].bond_type[bondNo] = types[bondNo];
              atoms[atNo].bond_stereo[bondNo] = stereo[bondNo];
          }

          // Free memory
          env->ReleaseIntArrayElements(neighbourArray, neighbours, 0);
          env->ReleaseIntArrayElements(typeArray, types, 0);
          env->ReleaseIntArrayElements(stereoArray, stereo, 0);

          return(true);
      } else {
            return(false);
        }
  }


/**
 * Add stereo parity definition.  See <tt>inchi_api.h</tt> for details.
 *
 * @param i            Parity Number
 * @param atC        Central atom
 * @param at0        Atom 0
 * @param at1        Atom 1
 * @param at2        Atom 2
 * @param at3        Atom 3
 * @param type        Stereo parity type
 * @param parity    Parity
 */
JNIEXPORT jboolean JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiSetStereo
  (JNIEnv *, jobject, jint i, jint atC, jint at0, jint at1, jint at2, jint at3,
      jint type, jint parity) {

      if (i > -1 && i < numberStereo) {

          stereo[i].central_atom = atC;
          stereo[i].neighbor[0] = at0;
          stereo[i].neighbor[1] = at1;
          stereo[i].neighbor[2] = at2;
          stereo[i].neighbor[3] = at3;

          stereo[i].type = type;
          stereo[i].parity = parity;

          return(true);
      } else {
            return(false);
        }
  }


/**
 * Generate InChI from loaded data.  See <tt>inchi_api.h</tt> for details.
 * @return    Return status
 */
JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGenerateInchi
  (JNIEnv *, jobject) {

      int ret = GetINCHI(pInp, pOut);

      szMessage = pOut->szMessage;
      szLog = pOut->szLog;

      return ret;
  }


/**
 * Fetches InChI string from InChI library.
 */
JNIEXPORT jstring JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetInchi
  (JNIEnv *env, jobject) {

      return env->NewStringUTF(pOut->szInChI);
  }


/**
 * Fetches AuxInfo string from InChI library.
 */
JNIEXPORT jstring JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetAuxInfo
  (JNIEnv *env, jobject) {

      return env->NewStringUTF(pOut->szAuxInfo);
  }


/**
 * Fetches Message string from InChI library.
 */
JNIEXPORT jstring JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetMessage
  (JNIEnv *env, jobject) {

      return env->NewStringUTF(szMessage);
  }


/**
 * Fetches Log string from InChI library.
 */
JNIEXPORT jstring JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetLog
  (JNIEnv *env, jobject) {

      return env->NewStringUTF(szLog);
  }


/**
 * Frees memory used by InChI library.  Must be called once InChI has
 * been generated and all data fetched.
 */
JNIEXPORT void JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiFreeInputMem
  (JNIEnv *, jobject) {

      Free_inchi_Input(pInp);
      delete optionString;
  }


/**
 * Frees memory used by InChI library.  Must be called once InChI has
 * been generated and all data fetched.
 */
JNIEXPORT void JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiFreeOutputMem
  (JNIEnv *, jobject) {

      FreeINCHI(pOut);
  }





/**
 * Generates structure from InChI string.
 * @param inchi        InChI string
 * @param options    Options
 * @return        Return status.
 */
JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetStruct
  (JNIEnv *env, jobject, jstring inchi, jstring options) {

      int len = env->GetStringLength(inchi);
      const char *nativeString = env->GetStringUTFChars(inchi, 0);
      char * inchiString = new char[len + 1];
      strcpy(inchiString, nativeString);
      inchiString[len] = 0;
      env->ReleaseStringUTFChars(inchi, nativeString);

      pInpInchi->szInChI = inchiString;

      len = env->GetStringLength(options);
      nativeString = env->GetStringUTFChars(options, 0);
      optionString = new char[len + 1];
      strcpy(optionString, nativeString);
      optionString[len] = 0;
      env->ReleaseStringUTFChars(options, nativeString);

      pInpInchi->szOptions = optionString;

      int ret = GetStructFromINCHI(pInpInchi, pOutStruct);

      numberAtoms = pOutStruct->num_atoms;
      numberStereo = pOutStruct->num_stereo0D;
      atoms = pOutStruct->atom;
      stereo = pOutStruct->stereo0D;

      szMessage = pOutStruct->szMessage;
      szLog = pOutStruct->szLog;

      return ret;
  }


/**
 * Generates InChI from InChI string.
 * @param inchi		InChI string
 * @param options	Options
 * @return		Return status.
 */
JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGenerateInchiFromInchi
  (JNIEnv *env, jobject, jstring inchi, jstring options) {

      int len = env->GetStringLength(inchi);
      const char *nativeString = env->GetStringUTFChars(inchi, 0);
      char * inchiString = new char[len + 1];
      strcpy(inchiString, nativeString);
      inchiString[len] = 0;
      env->ReleaseStringUTFChars(inchi, nativeString);

      pInpInchi->szInChI = inchiString;

      len = env->GetStringLength(options);
      nativeString = env->GetStringUTFChars(options, 0);
      optionString = new char[len + 1];
      strcpy(optionString, nativeString);
      optionString[len] = 0;
      env->ReleaseStringUTFChars(options, nativeString);

      pInpInchi->szOptions = optionString;

      int ret = GetINCHIfromINCHI(pInpInchi, pOut);

      szMessage = pOut->szMessage;
      szLog = pOut->szLog;

      return ret;
  }




/**
 * Fetches number of atoms in InChI structure.
 */
JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetNumAtoms
  (JNIEnv *, jobject) {

      return numberAtoms;
  }


/**
 * Fetches number of stereo parities in InChI structure.
 */
JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetNumStereo
  (JNIEnv *, jobject) {

      return numberStereo;
  }


/**
 * Fetches X coordinate of atom.
 * @param atIndx    Atom index number
 */
JNIEXPORT jdouble JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetAtomX
  (JNIEnv *, jobject, jint indx) {

      return atoms[indx].x;
  }


/**
 * Fetches Y coordinate of atom.
 * @param atIndx    Atom index number
 */
JNIEXPORT jdouble JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetAtomY
  (JNIEnv *, jobject, jint indx) {

      return atoms[indx].y;
  }


/**
 * Fetches Z coordinate of atom.
 * @param atIndx    Atom index number
 */
JNIEXPORT jdouble JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetAtomZ
  (JNIEnv *, jobject, jint indx) {

      return atoms[indx].y;
  }


/**
 * Fetches chemical element of atom.
 * @param atIndx    Atom index number
 */
JNIEXPORT jstring JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetAtomElement
  (JNIEnv *env, jobject, jint indx) {

      return env->NewStringUTF(atoms[indx].elname);
  }


/**
 * Fetches isotopic mass/isotopic mass shift of atom.
 * @param atIndx    Atom index number
 */
JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetAtomIsotopicMass
  (JNIEnv *, jobject, jint indx) {

      return atoms[indx].isotopic_mass;
  }


/**
 * Fetches radical status of atom.
 * @param atIndx    Atom index number
 */
JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetAtomRadical
  (JNIEnv *, jobject, jint indx) {

      return atoms[indx].radical;
  }


/**
 * Fetches charge on atom.
 * @param atIndx    Atom index number
 */
JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetAtomCharge
  (JNIEnv *, jobject, jint indx) {

      return atoms[indx].charge;
  }


/**
 * Fetches number of implicit hydrogens on atom.
 * @param atIndx    Atom index number
 */
JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetAtomImplicitH
  (JNIEnv *, jobject, jint indx) {

      return atoms[indx].num_iso_H[0];
  }


/**
 * Fetches number of implicit protium (1H) on atom.
 * @param atIndx    Atom index number
 */
JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetAtomImplicitP
  (JNIEnv *, jobject, jint indx) {

      return atoms[indx].num_iso_H[1];
  }


/**
 * Fetches number of implicit deuterium (2H) on atom.
 * @param atIndx    Atom index number
 */
JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetAtomImplicitD
  (JNIEnv *, jobject, jint indx) {

      return atoms[indx].num_iso_H[2];
  }


/**
 * Fetches number of implicit tritum (3H) on atom.
 * @param atIndx    Atom index number
 */
JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetAtomImplicitT
  (JNIEnv *, jobject, jint indx) {

      return atoms[indx].num_iso_H[3];
  }


/**
 * Fetches number of bonds on atom.
 * @param atIndx    Atom index number
 */
JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetAtomNumBonds
  (JNIEnv *, jobject, jint indx) {

      return atoms[indx].num_bonds;
  }


/**
 * Fetches index of neighbouring atom.
 * @param atIndx    Atom index number
 * @param bondIndx    Bond index number
 */
JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetAtomNeighbour
  (JNIEnv *, jobject, jint indx, jint i) {

      return atoms[indx].neighbor[i];
  }


/**
 * Fetches bond type (order).
 * @param atIndx    Atom index number
 * @param bondIndx    Bond index number
 */
JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetAtomBondType
  (JNIEnv *, jobject, jint indx, jint i) {

      return atoms[indx].bond_type[i];
  }


/**
 * Fetches bond stereochemistry.
 * @param atIndx    Atom index number
 * @param bondIndx    Bond index number
 */
JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetAtomBondStereo
  (JNIEnv *, jobject, jint indx, jint i) {

      return atoms[indx].bond_stereo[i];
  }



JNIEXPORT jlong JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetStructWarningFlags00
  (JNIEnv *env, jobject) {

      return pOutStruct->WarningFlags[0][0];
  }



JNIEXPORT jlong JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetStructWarningFlags01
  (JNIEnv *env, jobject) {

      return pOutStruct->WarningFlags[0][0];
  }



JNIEXPORT jlong JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetStructWarningFlags10
  (JNIEnv *env, jobject) {

      return pOutStruct->WarningFlags[0][0];
  }



JNIEXPORT jlong JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetStructWarningFlags11
  (JNIEnv *env, jobject) {

      return pOutStruct->WarningFlags[0][0];
  }


/**
 * Frees memory used by InChI library.  Must be called once structure has
 * been generated and all data fetched.
 */
JNIEXPORT void JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiFreeStructMem
  (JNIEnv *, jobject) {

      FreeStructFromINCHI(pOutStruct);
  }


JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetStereoCentralAtom
  (JNIEnv *, jobject, jint indx) {

      return stereo[indx].central_atom;
  }


JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetStereoNeighbourAtom
  (JNIEnv *, jobject, jint indx, jint i) {

      return stereo[indx].neighbor[i];
  }


JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetStereoType
  (JNIEnv *, jobject, jint indx) {

      return stereo[indx].type;
  }

JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetStereoParity
  (JNIEnv *, jobject, jint indx) {

      return stereo[indx].parity;
  }


JNIEXPORT jstring JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetVersion
  (JNIEnv *env, jclass) {
      return env->NewStringUTF(NATIVE_LIB_VERSION);
  }

