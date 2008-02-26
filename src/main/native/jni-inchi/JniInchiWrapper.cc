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
#include <vector>

#include "net_sf_jniinchi_JniInchiWrapper.h"
#include <inchi_api.h>
#include <ichisize.h>

#define NATIVE_LIB_VERSION "1.5"

// #define DEBUG 1

using namespace std;

// InChI DLL input data structure
// inchi_Input  inchi_inp, *pInp;  // = &inchi_inp;

// InChI DLL output data structure
//inchi_Output inchi_out, *pOut;  // = &inchi_out;

// InChI DLL data structure for InChI->Structure input
inchi_InputINCHI inpInChI, *pInpInchi;  // = &inpInChI;

// InChI DLL data structure for InChI->Structure output
inchi_OutputStruct outStruct, *pOutStruct;  // = &outStruct;

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

char * szInchi;
char * szAuxInfo;
char * szMessage;
char * szLog;

char * inchiKey = new char[26];





JNIEXPORT jstring JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetVersion
  (JNIEnv *env, jclass) {
      
      return env->NewStringUTF(NATIVE_LIB_VERSION);
      
  }





/**
 * Fetches InChI string from InChI library.
 */
JNIEXPORT jstring JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetInchi
  (JNIEnv *env, jobject) {

      jstring value = env->NewStringUTF(szInchi);
      return value;
      
  }


/**
 * Fetches AuxInfo string from InChI library.
 */
JNIEXPORT jstring JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetAuxInfo
  (JNIEnv *env, jobject) {

      jstring value = env->NewStringUTF(szAuxInfo);
      return value;
      
  }


/**
 * Fetches Message string from InChI library.
 */
JNIEXPORT jstring JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetMessage
  (JNIEnv *env, jobject) {

      jstring value = env->NewStringUTF(szMessage);
      return value;
      
  }


/**
 * Fetches Log string from InChI library.
 */
JNIEXPORT jstring JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetLog
  (JNIEnv *env, jobject) {

      jstring value = env->NewStringUTF(szLog);
      return value;
      
  }


/**
 * Frees memory used by InChI library.  Must be called once InChI has
 * been generated and all data fetched.
 */
JNIEXPORT void JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiFreeInputMem
  (JNIEnv *, jobject) {

//      Free_inchi_Input(pInp);
//      free(optionString);
//      delete atoms;
//      delete stereo;
  }


/**
 * Frees memory used by InChI library.  Must be called once InChI has
 * been generated and all data fetched.
 */
JNIEXPORT void JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiFreeOutputMem
  (JNIEnv *, jobject) {

      if (szMessage) {      
        delete szMessage;
        szMessage = 0;
      }
      if (szLog) {
          delete szLog;
          szLog = 0;
      }
      if (szInchi) {
          delete szInchi;
          szInchi = 0;
      }
      if (szAuxInfo) {
        delete szAuxInfo;
        szAuxInfo = 0;
      }
      
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

      
      inchi_Output inchi_out;
      memset(&inchi_out,0,sizeof(inchi_Output));
      
      int ret = GetINCHIfromINCHI(pInpInchi, &inchi_out);

      szMessage = new char[strlen(inchi_out.szMessage)];      //pOut->szMessage;
      strcpy(szMessage, inchi_out.szMessage);
      
      szLog = new char[strlen(inchi_out.szLog)];              //pOut->szLog;
      strcpy(szLog, inchi_out.szLog);
      
      szInchi = new char[strlen(inchi_out.szInChI)];
      strcpy(szInchi, inchi_out.szInChI);
      
      szAuxInfo = new char[strlen(inchi_out.szAuxInfo)];
      strcpy(szAuxInfo, inchi_out.szAuxInfo);


      return ret;
  }



  
 

  
  
  jmethodID getMethod(JNIEnv *env, jclass clazz, char *name, char *sig) {
      
      jmethodID method = env->GetMethodID(clazz, name, sig);
#ifdef DEBUG
      if (method == 0) {
          cerr << "method not found: " << name << " " << sig << "\n";
      } else {
          cerr << "method found: " << name << " " << sig << "\n";
      }
#endif      
      return method;
      
  }
  
  
  
  jclass IllegalArgumentException;
  
  jclass jniInchiInput, jniInchiOutput, jniInchiOutputKey;
  jclass jniInchiStructure, jniInchiAtom, jniInchiBond, jniInchiStereo0D;
    
  jmethodID getNumAtoms, getNumBonds, getNumStereo0D, getOptions, getAtom, getAtomIndex, getBond, getStereo0D;
  jmethodID addAtom, addBond, addStereo0D;
  
  jmethodID initJniInchiAtom, initJniInchiBond, initJniInchiStereo0D;
  jmethodID getElementType, getX, getY, getZ, getCharge, getRadical, getImplicitH, getImplicitProtium, getImplicitDeuterium, getImplicitTritium, getIsotopicMass;
  jmethodID setCharge, setRadical, setImplicitH, setImplicitProtium, setImplicitDeuterium, setImplicitTritium, setIsotopicMass;
  
  jmethodID getOriginAtom, getTargetAtom, getBondType, getBondStereo;
  jmethodID getCentralAtom, getNeighbor, getStereoType, getParity;
  
  jmethodID initJniInchiOutput, initJniInchiOutputKey;
  
  
  
  jclass jniInchiOutputStructure;
  jmethodID initJniInchiOutputStructure, setMessage, setLog, setWarningFlags;
  
  
  // Locates classes
  // Return 1 on success, 0 on failure.
  int initClasses(JNIEnv *env) {
      
      if (0 == (IllegalArgumentException = env->FindClass("java/lang/IllegalArgumentException"))) return 0;
      
      if (0 == (jniInchiInput = env->FindClass("net/sf/jniinchi/JniInchiInput"))) return 0;
      if (0 == (jniInchiOutput = env->FindClass("net/sf/jniinchi/JniInchiOutput"))) return 0;
        
      if (0 == (jniInchiStructure = env->FindClass("net/sf/jniinchi/JniInchiStructure"))) return 0;
      if (0 == (jniInchiAtom = env->FindClass("net/sf/jniinchi/JniInchiAtom"))) return 0;
      if (0 == (jniInchiBond = env->FindClass("net/sf/jniinchi/JniInchiBond"))) return 0;
      if (0 == (jniInchiStereo0D = env->FindClass("net/sf/jniinchi/JniInchiStereo0D"))) return 0;
      
      
        if (0 == (jniInchiOutputStructure = env->FindClass("net/sf/jniinchi/JniInchiOutputStructure"))) return 0;

      
      return 1;
  }
  
  
  
  
  int initInchiKey(JNIEnv *env) {
      
        if (0 == (jniInchiOutputKey = env->FindClass("net/sf/jniinchi/JniInchiOutputKey"))) return 0;
        if (0== (initJniInchiOutputKey = env->GetMethodID(jniInchiOutputKey, "<init>", "(ILjava/lang/String;)V"))) return 0;
       
        return 1;    
  }
  
  int initMethods(JNIEnv *env) {
      
    if (0== (getNumAtoms = env->GetMethodID(jniInchiStructure, "getNumAtoms", "()I"))) return 0;
    if (0== (getNumBonds = env->GetMethodID(jniInchiStructure, "getNumBonds", "()I"))) return 0;
    if (0== (getNumStereo0D = env->GetMethodID(jniInchiStructure, "getNumStereo0D", "()I"))) return 0;
    if (0== (getOptions = env->GetMethodID(jniInchiInput, "getOptions", "()Ljava/lang/String;"))) return 0;
    if (0== (getAtom = env->GetMethodID(jniInchiStructure, "getAtom", "(I)Lnet/sf/jniinchi/JniInchiAtom;"))) return 0;
    if (0== (getBond = env->GetMethodID(jniInchiStructure, "getBond", "(I)Lnet/sf/jniinchi/JniInchiBond;"))) return 0;
    if (0== (getStereo0D = env->GetMethodID(jniInchiStructure, "getStereo0D", "(I)Lnet/sf/jniinchi/JniInchiStereo0D;"))) return 0;
    if (0== (getAtomIndex = env->GetMethodID(jniInchiStructure, "getAtomIndex", "(Lnet/sf/jniinchi/JniInchiAtom;)I"))) return 0;
            
    if (0== (addAtom = env->GetMethodID(jniInchiStructure, "addAtom", "(Lnet/sf/jniinchi/JniInchiAtom;)Lnet/sf/jniinchi/JniInchiAtom;"))) return 0;
    if (0== (addBond = env->GetMethodID(jniInchiStructure, "addBond", "(Lnet/sf/jniinchi/JniInchiBond;)Lnet/sf/jniinchi/JniInchiBond;"))) return 0;
    if (0== (addStereo0D = env->GetMethodID(jniInchiStructure, "addStereo0D", "(Lnet/sf/jniinchi/JniInchiStereo0D;)Lnet/sf/jniinchi/JniInchiStereo0D;"))) return 0;
    
    if (0== (initJniInchiAtom = env->GetMethodID(jniInchiAtom, "<init>", "(DDDLjava/lang/String;)V"))) return 0;
    if (0== (initJniInchiBond = env->GetMethodID(jniInchiBond, "<init>", "(Lnet/sf/jniinchi/JniInchiAtom;Lnet/sf/jniinchi/JniInchiAtom;II)V"))) return 0;
    if (0== (initJniInchiStereo0D = env->GetMethodID(jniInchiStereo0D, "<init>", "(Lnet/sf/jniinchi/JniInchiAtom;Lnet/sf/jniinchi/JniInchiAtom;Lnet/sf/jniinchi/JniInchiAtom;Lnet/sf/jniinchi/JniInchiAtom;Lnet/sf/jniinchi/JniInchiAtom;II)V"))) return 0;
    
    if (0== (getElementType = env->GetMethodID(jniInchiAtom, "getElementType", "()Ljava/lang/String;"))) return 0;
    if (0== (getX = env->GetMethodID(jniInchiAtom, "getX", "()D"))) return 0;
    if (0== (getY = env->GetMethodID(jniInchiAtom, "getY", "()D"))) return 0;
    if (0== (getZ = env->GetMethodID(jniInchiAtom, "getZ", "()D"))) return 0;
    if (0== (getCharge = env->GetMethodID(jniInchiAtom, "getCharge", "()I"))) return 0;
    if (0== (getRadical = env->GetMethodID(jniInchiAtom, "getInchiRadical", "()I"))) return 0;
    if (0== (getImplicitH = env->GetMethodID(jniInchiAtom, "getImplicitH", "()I"))) return 0;
    if (0== (getImplicitProtium = env->GetMethodID(jniInchiAtom, "getImplicitProtium", "()I"))) return 0;
    if (0== (getImplicitDeuterium = env->GetMethodID(jniInchiAtom, "getImplicitDeuterium", "()I"))) return 0;
    if (0== (getImplicitTritium = env->GetMethodID(jniInchiAtom, "getImplicitTritium", "()I"))) return 0;
    if (0== (getIsotopicMass = env->GetMethodID(jniInchiAtom, "getIsotopicMass", "()I"))) return 0;
    
    if (0== (setCharge = env->GetMethodID(jniInchiAtom, "setCharge", "(I)V"))) return 0;
    if (0== (setRadical = env->GetMethodID(jniInchiAtom, "setInchiRadical", "(I)V"))) return 0;
    if (0== (setImplicitH = env->GetMethodID(jniInchiAtom, "setImplicitH", "(I)V"))) return 0;
    if (0== (setImplicitProtium = env->GetMethodID(jniInchiAtom, "setImplicitProtium", "(I)V"))) return 0;
    if (0== (setImplicitDeuterium = env->GetMethodID(jniInchiAtom, "setImplicitDeuterium", "(I)V"))) return 0;
    if (0== (setImplicitTritium = env->GetMethodID(jniInchiAtom, "setImplicitTritium", "(I)V"))) return 0;
    if (0== (setIsotopicMass = env->GetMethodID(jniInchiAtom, "setIsotopicMass", "(I)V"))) return 0;
    
    if (0== (getOriginAtom = env->GetMethodID(jniInchiBond, "getOriginAtom", "()Lnet/sf/jniinchi/JniInchiAtom;"))) return 0;
    if (0== (getTargetAtom = env->GetMethodID(jniInchiBond, "getTargetAtom", "()Lnet/sf/jniinchi/JniInchiAtom;"))) return 0;
    if (0== (getBondType = env->GetMethodID(jniInchiBond, "getInchiBondType", "()I"))) return 0;
    if (0== (getBondStereo = env->GetMethodID(jniInchiBond, "getInchiBondStereo", "()I"))) return 0;

    if (0== (getCentralAtom = env->GetMethodID(jniInchiStereo0D, "getCentralAtom", "()Lnet/sf/jniinchi/JniInchiAtom;"))) return 0;
    if (0== (getNeighbor = env->GetMethodID(jniInchiStereo0D, "getNeighbor", "(I)Lnet/sf/jniinchi/JniInchiAtom;"))) return 0;
    if (0== (getStereoType = env->GetMethodID(jniInchiStereo0D, "getInchiStereoType", "()I"))) return 0;
    if (0== (getParity = env->GetMethodID(jniInchiStereo0D, "getInchiParity", "()I"))) return 0;
            
    if (0== (initJniInchiOutput = env->GetMethodID(jniInchiOutput, "<init>", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V"))) return 0;
    
    
    if (0== (initJniInchiOutputStructure = env->GetMethodID(jniInchiOutputStructure, "<init>", "(I)V"))) return 0;
    if (0== (setMessage = env->GetMethodID(jniInchiOutputStructure, "setMessage", "(Ljava/lang/String;)V"))) return 0;
    if (0== (setLog = env->GetMethodID(jniInchiOutputStructure, "setLog", "(Ljava/lang/String;)V"))) return 0;
    if (0== (setWarningFlags= env->GetMethodID(jniInchiOutputStructure, "setWarningFlags", "(JJJJ)V"))) return 0;
    
    return 1;
  }
  
  
  
  
    int init(JNIEnv *env) {
        
        if (0 == initClasses(env)) {
            return 0;
        }
        if (0 == initMethods(env)) {
            return 0;
        }
        
        return 1;
    }
    
  
  
  JNIEXPORT jobject JNICALL Java_net_sf_jniinchi_JniInchiWrapper_getINCHI
    (JNIEnv *env, jobject obj, jobject input) {
        
        if (0 == init(env)) {
            return 0;
        }
        
        jboolean iscopy = JNI_TRUE;
        
        
        jint natoms = env->CallIntMethod(input, getNumAtoms);
        jint nstereo = env->CallIntMethod(input, getNumStereo0D);
        jint nbonds = env->CallIntMethod(input, getNumBonds);
        
        if (natoms > MAX_ATOMS) {
            env->ThrowNew(IllegalArgumentException, "Too many atoms; maximum: " + MAX_ATOMS);
            return 0;
        }
        
#ifdef DEBUG
        cerr << "natoms: " << natoms << "\n";
        cerr << "nbonds: " << nbonds << "\n";
        cerr << "nstereo: " << nstereo << "\n";
#endif
        
        
        vector<inchi_Atom> atoms(natoms);
        for (int i = 0; i < natoms; i++) {
          
#ifdef DEBUG            
            cerr << "atom #" << i;
#endif
            jobject atom = env->CallObjectMethod(input, getAtom, i);
    
            inchi_Atom& iatom = atoms[i];
            memset(&iatom,0,sizeof(inchi_Atom));

            
            jstring jelname = (jstring) env->CallObjectMethod(atom, getElementType);
            const char *elname = env->GetStringUTFChars(jelname, &iscopy);
            if (strlen(elname) > ATOM_EL_LEN) {
                env->ThrowNew(IllegalArgumentException, "Element name too long; maximum: " + ATOM_EL_LEN);
                return 0;
            }
            
            strcpy(iatom.elname, elname);
            
#ifdef DEBUG            
            cerr << " " << (*elname);
#endif
            
            env->ReleaseStringUTFChars(jelname, elname);
        
            jdouble x = env->CallDoubleMethod(atom, getX);
            jdouble y = env->CallDoubleMethod(atom, getY);
            jdouble z = env->CallDoubleMethod(atom, getZ);

#ifdef DEBUG                
            cerr << " [" << x << "," << y << "," << z << "]";
#endif

            iatom.x = x;
            iatom.y = y;
            iatom.z = z;
  
            jint charge = env->CallIntMethod(atom, getCharge);
            iatom.charge = charge;

#ifdef DEBUG
            cerr << " chrg:" << charge;
#endif

            jint radical = env->CallIntMethod(atom, getRadical);
            iatom.radical = radical;
            
            jint implicitH = env->CallIntMethod(atom, getImplicitH);
            iatom.num_iso_H[0] = implicitH;
            
            jint implicitProtium = env->CallIntMethod(atom, getImplicitProtium);
            iatom.num_iso_H[1] = implicitProtium;
            
            jint implicitDeuterium = env->CallIntMethod(atom, getImplicitDeuterium);
            iatom.num_iso_H[2] = implicitDeuterium;
            
            jint implicitTritum = env->CallIntMethod(atom, getImplicitTritium);
            iatom.num_iso_H[3] = implicitTritum;
  
            jint isotopicMass = env->CallIntMethod(atom, getIsotopicMass);
            iatom.isotopic_mass = isotopicMass;

#ifdef DEBUG
            cerr << "\n";
#endif            
        } 
        
        
        
        for (int i = 0; i < nbonds; i++) {
            
            jobject bond = env->CallObjectMethod(input, getBond, i);
            jobject atomO = env->CallObjectMethod(bond, getOriginAtom);
            jobject atomT = env->CallObjectMethod(bond, getTargetAtom);
            jint bondType = env->CallIntMethod(bond, getBondType);
            jint bondStereo = env->CallIntMethod(bond, getBondStereo);
            
            jint iaO = env->CallIntMethod(input, getAtomIndex, atomO);
            jint iaT = env->CallIntMethod(input, getAtomIndex, atomT);
            
            
#ifdef DEBUG
            cerr << "bond#" << i << ": a#" << iaO << ",a#" << iaT << " (" << bondType << ")\n";
#endif
            
            
            inchi_Atom& iatom = atoms[iaO];
            int numbonds = iatom.num_bonds;
            if (numbonds == MAXVAL) {
                env->ThrowNew(IllegalArgumentException, "Too many bonds from one atom; maximum: " + MAXVAL);
                return 0;
            }
            iatom.neighbor[numbonds] = iaT;
            iatom.bond_type[numbonds] = bondType;
            iatom.bond_stereo[numbonds] = bondStereo;
            iatom.num_bonds++;
            
        }
        
        
        
        vector<inchi_Stereo0D> stereos(nstereo);
        for (int i = 0; i < nstereo; i++) {
            
            jobject stereo = env->CallObjectMethod(input, getStereo0D, i);
    
            inchi_Stereo0D &istereo = stereos[i];
            memset(&istereo,0,sizeof(inchi_Stereo0D));
            
            jobject cat = env->CallObjectMethod(stereo, getCentralAtom);            
            jobject nat0 = env->CallObjectMethod(stereo, getNeighbor, 0);
            jobject nat1 = env->CallObjectMethod(stereo, getNeighbor, 1);
            jobject nat2 = env->CallObjectMethod(stereo, getNeighbor, 2);
            jobject nat3 = env->CallObjectMethod(stereo, getNeighbor, 3);
            
            jint iac = env->CallIntMethod(input, getAtomIndex, cat);
            jint in0 = env->CallIntMethod(input, getAtomIndex, nat0);
            jint in1 = env->CallIntMethod(input, getAtomIndex, nat1);
            jint in2 = env->CallIntMethod(input, getAtomIndex, nat2);
            jint in3 = env->CallIntMethod(input, getAtomIndex, nat3);
            
            jint stereoType = env->CallIntMethod(stereo, getStereoType);
            jint parity = env->CallIntMethod(stereo, getParity);
            
            istereo.central_atom = iac;
            istereo.neighbor[0] = in0;
            istereo.neighbor[1] = in1;
            istereo.neighbor[2] = in2;
            istereo.neighbor[3] = in3;
            istereo.type = stereoType;
            istereo.parity = parity;
            
        }
        
        inchi_Input inchi_input;
        memset(&inchi_input,0,sizeof(inchi_Input));
        
        
        jstring joptions = (jstring) env->CallObjectMethod(input, getOptions);
        const char *options = env->GetStringUTFChars(joptions, &iscopy);
        char *opts = new char[strlen(options)+1];
        strcpy(opts, options);
   
#ifdef DEBUG        
        cerr << "options: " << opts << "\n";
#endif        
        
        inchi_input.szOptions = opts;
        env->ReleaseStringUTFChars(joptions, options);

        
        inchi_input.num_atoms = natoms;
        inchi_input.num_stereo0D = nstereo;
        
        inchi_input.atom = &atoms[0];
        inchi_input.stereo0D = &stereos[0];
        
        
        inchi_Output inchi_output;
        memset(&inchi_output,0,sizeof(inchi_Output));
        
#ifdef DEBUG
        cerr << "generating InChI...";
#endif
        
        int ret = GetINCHI(&inchi_input, &inchi_output);
        
        
#ifdef DEBUG
        cerr << ret << "\n";
        cerr << "ret: " << ret << "\n";
        cerr << "message: " << inchi_output.szMessage << "\n";
        cerr << "inchi: " << inchi_output.szInChI << "\n";
#endif  

        jobject output = env->NewObject(jniInchiOutput, initJniInchiOutput,
                ret,
                env->NewStringUTF(inchi_output.szInChI),
                env->NewStringUTF(inchi_output.szAuxInfo),
                env->NewStringUTF(inchi_output.szMessage),
                env->NewStringUTF(inchi_output.szLog));
        
        delete[] opts;
        FreeINCHI(&inchi_output);

        return output;
  }






  JNIEXPORT jobject JNICALL Java_net_sf_jniinchi_JniInchiWrapper_getINCHIKeyFromINCHI
    (JNIEnv *env, jobject obj, jstring inchi) {
        
        if (0 == initInchiKey(env)) {
            return 0;
        }
        
        char *inchiKey = new char[26];
        const char *inchiString = env->GetStringUTFChars(inchi, 0);
        
#ifdef DEBUG        
        cerr << "inchi: " << inchi << "\n";
#endif        
        
        int ret = GetINCHIKeyFromINCHI(inchiString, inchiKey);
        env->ReleaseStringUTFChars(inchi, inchiString);
      
#ifdef DEBUG        
        cerr << "ret: " << ret << "\n";
        cerr << "key: " << inchiKey << "\n";
#endif
        
        jstring key = env->NewStringUTF(inchiKey);
        delete[] inchiKey;
        return env->NewObject(jniInchiOutputKey, initJniInchiOutputKey, ret, key);
        
    }

    

    JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_CheckINCHIKey
  (JNIEnv *env, jobject, jstring key) {

      // Get inchi string
      const char *keyString = env->GetStringUTFChars(key, 0);
      int ret = CheckINCHIKey(keyString);
      env->ReleaseStringUTFChars(key, keyString);

      return ret;
  }
  
  
  
  
  
  
    JNIEXPORT jobject Java_net_sf_jniinchi_JniInchiWrapper_GetStructFromINCHI
  (JNIEnv *env, jobject, jstring inchi, jstring options) {
      
        if (0 == init(env)) {
            return 0;
        }
        
        const char *inchiString = env->GetStringUTFChars(inchi, 0);
        const char *optionsString = env->GetStringUTFChars(options, 0);
        
        char *szInchi = new char[strlen(inchiString)+1];
        strcpy(szInchi, inchiString);
        char *szOptions = new char[strlen(optionsString)+1];
        strcpy(szOptions, optionsString);
        
        env->ReleaseStringUTFChars(inchi, inchiString);
        env->ReleaseStringUTFChars(options, optionsString);
      
#ifdef DEBUG        
        cerr << "inchi: " << szInchi << "\n";
        cerr << "options: " << szOptions << "\n";
#endif
        
        inchi_InputINCHI inchi_input;
        memset(&inchi_input,0,sizeof(inchi_InputINCHI));
        
        inchi_input.szInChI = szInchi;
        inchi_input.szOptions = szOptions;
        
        inchi_OutputStruct inchi_output;
        memset(&inchi_output,0,sizeof(inchi_OutputStruct));
        
        
        int ret = GetStructFromINCHI(&inchi_input, &inchi_output);
        
#ifdef DEBUG
      cerr << "ret: " << ret << "\n";
#endif


        
        jobject output = env->NewObject(jniInchiOutputStructure, initJniInchiOutputStructure, ret);
        
        env->CallVoidMethod(output, setMessage, env->NewStringUTF(inchi_output.szMessage));
        env->CallVoidMethod(output, setLog, env->NewStringUTF(inchi_output.szLog));

        env->CallVoidMethod(output, setWarningFlags,
                inchi_output.WarningFlags[0][0],
                inchi_output.WarningFlags[0][1],
                inchi_output.WarningFlags[1][0],
                inchi_output.WarningFlags[1][1]);
        
        
        int numatoms = inchi_output.num_atoms;
        int numstereo = inchi_output.num_stereo0D;
        
        
        for (int i = 0; i < numatoms; i++) {
      
            inchi_Atom iatom = inchi_output.atom[i];

#ifdef DEBUG            
            cerr << "atom#" << i << " : " << iatom.elname << "\n";
#endif
            
            jobject atom = env->NewObject(jniInchiAtom, initJniInchiAtom, 
                    iatom.x,
                    iatom.y,
                    iatom.z,
                    env->NewStringUTF(iatom.elname));
            
            env->CallVoidMethod(atom, setCharge, iatom.charge);
            env->CallVoidMethod(atom, setRadical, iatom.radical);
            env->CallVoidMethod(atom, setImplicitH, iatom.num_iso_H[0]);
            env->CallVoidMethod(atom, setImplicitProtium, iatom.num_iso_H[1]);
            env->CallVoidMethod(atom, setImplicitDeuterium, iatom.num_iso_H[2]);
            env->CallVoidMethod(atom, setImplicitTritium, iatom.num_iso_H[3]);
            env->CallVoidMethod(atom, setIsotopicMass, iatom.isotopic_mass);
            
            env->CallVoidMethod(output, addAtom, atom);
            
        }
      
        
        // Bonds
        
        for (int i = 0; i < numatoms; i++) {
      
            inchi_Atom iatom = inchi_output.atom[i];
            int numbonds = iatom.num_bonds;
            
            if (numbonds > 0) {
            
                jobject atO = env->CallObjectMethod(output, getAtom, i);
                
                for (int j = 0; j < numbonds; j++) {
                    
                    // Bonds get recorded twice... 
                    if (iatom.neighbor[j] < i) {
                        
#ifdef DEBUG
                        cerr << "bond: a#" << i << "-a#" << iatom.neighbor[j] << "\n";
#endif                    
                    
                        jobject atT = env->CallObjectMethod(output, getAtom, iatom.neighbor[j]);
                        jobject bond = env->NewObject(jniInchiBond, initJniInchiBond, atO, atT, iatom.bond_type[j], iatom.bond_stereo[j]);
                        env->CallVoidMethod(output, addBond, bond);
                        
                    }
                }
            
            }            
        }
        
        
        for (int i = 0; i < numstereo; i++) {
      
            inchi_Stereo0D istereo = inchi_output.stereo0D[i];
            
            jobject atC = NULL;
            if (istereo.central_atom != NO_ATOM) {
                atC = env->CallObjectMethod(output, getAtom, istereo.central_atom);
            }
            jobject an0 = env->CallObjectMethod(output, getAtom, istereo.neighbor[0]);
            jobject an1 = env->CallObjectMethod(output, getAtom, istereo.neighbor[1]);
            jobject an2 = env->CallObjectMethod(output, getAtom, istereo.neighbor[2]);
            jobject an3 = env->CallObjectMethod(output, getAtom, istereo.neighbor[3]);
            
            jobject stereo = env->NewObject(jniInchiStereo0D, initJniInchiStereo0D,
                        atC,
                        an0,
                        an1,
                        an2,
                        an3,
                        istereo.type,
                        istereo.parity);
            
            env->CallVoidMethod(output, addStereo0D, stereo);
        }
      
        
        
        
        FreeStructFromINCHI(&inchi_output);
      
        return output;
      
  }
