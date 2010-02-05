/*
 * JNI InChI Wrapper - A Java Native Interface Wrapper for InChI.
 * Copyright (C) 2006-2008  Sam Adams
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

#define NATIVE_LIB_VERSION "1.6"

// #define DEBUG 1

using namespace std;


// === CLASS REFs === //

jclass IllegalArgumentException;

jclass jniInchiInput, jniInchiOutput, jniInchiOutputKey, jniInchiOutputStructure;
jclass jniInchiStructure, jniInchiAtom, jniInchiBond, jniInchiStereo0D;

// === METHOD REFs === //

// constructors
jmethodID initJniInchiOutput, initJniInchiOutputKey;
jmethodID initJniInchiAtom, initJniInchiBond, initJniInchiStereo0D;
jmethodID initJniInchiOutputStructure;

// structure methods
jmethodID getNumAtoms, getNumBonds, getNumStereo0D, getOptions, getAtom, getAtomIndex, getBond, getStereo0D;
jmethodID addAtom, addBond, addStereo0D;

// atom methods
jmethodID getElementType, getX, getY, getZ, getCharge, getRadical, getImplicitH, getImplicitProtium, getImplicitDeuterium, getImplicitTritium, getIsotopicMass;
jmethodID setCharge, setRadical, setImplicitH, setImplicitProtium, setImplicitDeuterium, setImplicitTritium, setIsotopicMass;

// bond methods
jmethodID getOriginAtom, getTargetAtom, getBondType, getBondStereo;

// stereo0D methods
jmethodID getCentralAtom, getNeighbor, getStereoType, getParity;





// === CHECK NATIVE CODE VERSION === //

JNIEXPORT jstring JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetVersion
    (JNIEnv *env, jclass) {
      
        return env->NewStringUTF(NATIVE_LIB_VERSION);
      
}


// === JNI INITIALIZERS === //


//
// Initialises the jclass pointer clazz to point to the named class
//
bool initClass(JNIEnv *env, jclass *clazz, char *name) {
    
    if (NULL != (*clazz)) {
        return true;
    }
    
    jclass clz = env->FindClass(name);
    if (clz == NULL) {
        return false;
    }
    
    (*clazz) = (jclass) env->NewGlobalRef(clz);
    if ((*clazz) == NULL) {
        return false;
    }
    
    return true;
    
}


bool initClassRefs(JNIEnv *env) {
    
    if (!initClass(env, &IllegalArgumentException, "java/lang/IllegalArgumentException")) return false;
  
    if (!initClass(env, &jniInchiInput, "net/sf/jniinchi/JniInchiInput")) return false;
    if (!initClass(env, &jniInchiOutput, "net/sf/jniinchi/JniInchiOutput")) return false;
    if (!initClass(env, &jniInchiOutputStructure, "net/sf/jniinchi/JniInchiOutputStructure")) return false;
    if (!initClass(env, &jniInchiOutputKey, "net/sf/jniinchi/JniInchiOutputKey")) return false;

    if (!initClass(env, &jniInchiStructure, "net/sf/jniinchi/JniInchiStructure")) return false;
    if (!initClass(env, &jniInchiAtom, "net/sf/jniinchi/JniInchiAtom")) return false;
    if (!initClass(env, &jniInchiBond, "net/sf/jniinchi/JniInchiBond")) return false;
    if (!initClass(env, &jniInchiStereo0D, "net/sf/jniinchi/JniInchiStereo0D")) return false;

    return true;
    
}


bool initMethodRefs(JNIEnv *env) {
    
    if (0== (getNumAtoms = env->GetMethodID(jniInchiStructure, "getNumAtoms", "()I"))) return false;
    if (0== (getNumBonds = env->GetMethodID(jniInchiStructure, "getNumBonds", "()I"))) return false;
    if (0== (getNumStereo0D = env->GetMethodID(jniInchiStructure, "getNumStereo0D", "()I"))) return false;
    if (0== (getOptions = env->GetMethodID(jniInchiInput, "getOptions", "()Ljava/lang/String;"))) return false;
    if (0== (getAtom = env->GetMethodID(jniInchiStructure, "getAtom", "(I)Lnet/sf/jniinchi/JniInchiAtom;"))) return false;
    if (0== (getBond = env->GetMethodID(jniInchiStructure, "getBond", "(I)Lnet/sf/jniinchi/JniInchiBond;"))) return false;
    if (0== (getStereo0D = env->GetMethodID(jniInchiStructure, "getStereo0D", "(I)Lnet/sf/jniinchi/JniInchiStereo0D;"))) return false;
    if (0== (getAtomIndex = env->GetMethodID(jniInchiStructure, "getAtomIndex", "(Lnet/sf/jniinchi/JniInchiAtom;)I"))) return false;
            
    if (0== (addAtom = env->GetMethodID(jniInchiStructure, "addAtom", "(Lnet/sf/jniinchi/JniInchiAtom;)Lnet/sf/jniinchi/JniInchiAtom;"))) return false;
    if (0== (addBond = env->GetMethodID(jniInchiStructure, "addBond", "(Lnet/sf/jniinchi/JniInchiBond;)Lnet/sf/jniinchi/JniInchiBond;"))) return false;
    if (0== (addStereo0D = env->GetMethodID(jniInchiStructure, "addStereo0D", "(Lnet/sf/jniinchi/JniInchiStereo0D;)Lnet/sf/jniinchi/JniInchiStereo0D;"))) return false;
    
    if (0== (initJniInchiAtom = env->GetMethodID(jniInchiAtom, "<init>", "(DDDLjava/lang/String;)V"))) return false;
    if (0== (initJniInchiBond = env->GetMethodID(jniInchiBond, "<init>", "(Lnet/sf/jniinchi/JniInchiAtom;Lnet/sf/jniinchi/JniInchiAtom;II)V"))) return false;
    if (0== (initJniInchiStereo0D = env->GetMethodID(jniInchiStereo0D, "<init>", "(Lnet/sf/jniinchi/JniInchiAtom;Lnet/sf/jniinchi/JniInchiAtom;Lnet/sf/jniinchi/JniInchiAtom;Lnet/sf/jniinchi/JniInchiAtom;Lnet/sf/jniinchi/JniInchiAtom;II)V"))) return false;
    
    if (0== (getElementType = env->GetMethodID(jniInchiAtom, "getElementType", "()Ljava/lang/String;"))) return false;
    if (0== (getX = env->GetMethodID(jniInchiAtom, "getX", "()D"))) return false;
    if (0== (getY = env->GetMethodID(jniInchiAtom, "getY", "()D"))) return false;
    if (0== (getZ = env->GetMethodID(jniInchiAtom, "getZ", "()D"))) return false;
    if (0== (getCharge = env->GetMethodID(jniInchiAtom, "getCharge", "()I"))) return false;
    if (0== (getRadical = env->GetMethodID(jniInchiAtom, "getInchiRadical", "()I"))) return false;
    if (0== (getImplicitH = env->GetMethodID(jniInchiAtom, "getImplicitH", "()I"))) return false;
    if (0== (getImplicitProtium = env->GetMethodID(jniInchiAtom, "getImplicitProtium", "()I"))) return false;
    if (0== (getImplicitDeuterium = env->GetMethodID(jniInchiAtom, "getImplicitDeuterium", "()I"))) return false;
    if (0== (getImplicitTritium = env->GetMethodID(jniInchiAtom, "getImplicitTritium", "()I"))) return false;
    if (0== (getIsotopicMass = env->GetMethodID(jniInchiAtom, "getIsotopicMass", "()I"))) return false;
    
    if (0== (setCharge = env->GetMethodID(jniInchiAtom, "setCharge", "(I)V"))) return false;
    if (0== (setRadical = env->GetMethodID(jniInchiAtom, "setInchiRadical", "(I)V"))) return false;
    if (0== (setImplicitH = env->GetMethodID(jniInchiAtom, "setImplicitH", "(I)V"))) return false;
    if (0== (setImplicitProtium = env->GetMethodID(jniInchiAtom, "setImplicitProtium", "(I)V"))) return false;
    if (0== (setImplicitDeuterium = env->GetMethodID(jniInchiAtom, "setImplicitDeuterium", "(I)V"))) return false;
    if (0== (setImplicitTritium = env->GetMethodID(jniInchiAtom, "setImplicitTritium", "(I)V"))) return false;
    if (0== (setIsotopicMass = env->GetMethodID(jniInchiAtom, "setIsotopicMass", "(I)V"))) return false;
    
    if (0== (getOriginAtom = env->GetMethodID(jniInchiBond, "getOriginAtom", "()Lnet/sf/jniinchi/JniInchiAtom;"))) return false;
    if (0== (getTargetAtom = env->GetMethodID(jniInchiBond, "getTargetAtom", "()Lnet/sf/jniinchi/JniInchiAtom;"))) return false;
    if (0== (getBondType = env->GetMethodID(jniInchiBond, "getInchiBondType", "()I"))) return false;
    if (0== (getBondStereo = env->GetMethodID(jniInchiBond, "getInchiBondStereo", "()I"))) return false;

    if (0== (getCentralAtom = env->GetMethodID(jniInchiStereo0D, "getCentralAtom", "()Lnet/sf/jniinchi/JniInchiAtom;"))) return false;
    if (0== (getNeighbor = env->GetMethodID(jniInchiStereo0D, "getNeighbor", "(I)Lnet/sf/jniinchi/JniInchiAtom;"))) return false;
    if (0== (getStereoType = env->GetMethodID(jniInchiStereo0D, "getInchiStereoType", "()I"))) return false;
    if (0== (getParity = env->GetMethodID(jniInchiStereo0D, "getInchiParity", "()I"))) return false;
            
    if (0== (initJniInchiOutput = env->GetMethodID(jniInchiOutput, "<init>", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V"))) return false;
    
    
    if (0== (initJniInchiOutputStructure = env->GetMethodID(jniInchiOutputStructure, "<init>", "(ILjava/lang/String;Ljava/lang/String;JJJJ)V"))) return false;
  
    if (0== (initJniInchiOutputKey = env->GetMethodID(jniInchiOutputKey, "<init>", "(ILjava/lang/String;)V"))) return false;
  
    return true;
}



JNIEXPORT void JNICALL Java_net_sf_jniinchi_JniInchiWrapper_init
    (JNIEnv *env, jobject obj) {
    
    if (!initClassRefs(env)) {
        return;
    }
    initMethodRefs(env);
    
}



  
  
tagINCHI_InputINCHI getInchiInputINCHI(JNIEnv *env, jstring inchi, jstring options) {
      
    // Convert java.lang.String inchi to char*
    const char *inchiString = env->GetStringUTFChars(inchi, 0);
    char *szInchi = new char[strlen(inchiString)+1];
    strcpy(szInchi, inchiString);
    env->ReleaseStringUTFChars(inchi, inchiString);
  
    // Convert java.lang.String options to char*
    const char *optionsString = env->GetStringUTFChars(options, 0);
    char *szOptions = new char[strlen(optionsString)+1];
    strcpy(szOptions, optionsString);
    env->ReleaseStringUTFChars(options, optionsString);
  
    #ifdef DEBUG        
    cerr << "inchi: " << szInchi << "\n";
    cerr << "options: " << szOptions << "\n";
    #endif
    
    tagINCHI_InputINCHI input;                       // Allocate memory
    memset(&input, 0, sizeof(tagINCHI_InputINCHI));  // Set initial values to 0
    
    input.szInChI = szInchi;
    input.szOptions = szOptions;
    
    return input;

}
  

    
jobject getInchiOutput(JNIEnv *env, int ret, tagINCHI_Output inchi_output) {
        
    #ifdef DEBUG
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
  
    return output;
      
}
  





// === STRUCTURE to INCHI == //

JNIEXPORT jobject JNICALL Java_net_sf_jniinchi_JniInchiWrapper_GetStdINCHI
    (JNIEnv *env, jobject obj, jobject input) {
        
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
    
    
    inchi_Atom *atoms = new inchi_Atom[natoms];
    // vector<inchi_Atom> atoms(natoms);
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
            env->ReleaseStringUTFChars(jelname, elname);
            return 0;
        }
        strcpy(iatom.elname, elname);
        env->ReleaseStringUTFChars(jelname, elname);
    
        #ifdef DEBUG            
        cerr << " " << iatom.elname;
        #endif
        
        iatom.x = env->CallDoubleMethod(atom, getX);
        iatom.y = env->CallDoubleMethod(atom, getY);
        iatom.z = env->CallDoubleMethod(atom, getZ);

        #ifdef DEBUG                
        cerr << " [" << iatom.x << "," << iatom.y << "," << iatom.z << "]";
        #endif
        
        iatom.charge = env->CallIntMethod(atom, getCharge);
        iatom.radical = env->CallIntMethod(atom, getRadical);
        
        iatom.num_iso_H[0] = env->CallIntMethod(atom, getImplicitH);
        iatom.num_iso_H[1] = env->CallIntMethod(atom, getImplicitProtium);
        iatom.num_iso_H[2] = env->CallIntMethod(atom, getImplicitDeuterium);
        iatom.num_iso_H[3] = env->CallIntMethod(atom, getImplicitTritium);

        iatom.isotopic_mass = env->CallIntMethod(atom, getIsotopicMass);

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
        
        inchi_Atom &iatom = atoms[iaO];
        int numbonds = iatom.num_bonds;
        if (numbonds == MAXVAL) {
            env->ThrowNew(IllegalArgumentException, "Too many bonds from one atom; maximum: " + MAXVAL);
            delete[] atoms;
            return 0;
        }
        iatom.neighbor[numbonds] = iaT;
        iatom.bond_type[numbonds] = bondType;
        iatom.bond_stereo[numbonds] = bondStereo;
        iatom.num_bonds++;
        
    }
    
    
    
    inchi_Stereo0D *stereos = new inchi_Stereo0D[nstereo];
    for (int i = 0; i < nstereo; i++) {
        
        jobject stereo = env->CallObjectMethod(input, getStereo0D, i);

        inchi_Stereo0D &istereo = stereos[i];
        memset(&istereo,0,sizeof(inchi_Stereo0D));
        
        jobject cat = env->CallObjectMethod(stereo, getCentralAtom);            
        jobject nat0 = env->CallObjectMethod(stereo, getNeighbor, 0);
        jobject nat1 = env->CallObjectMethod(stereo, getNeighbor, 1);
        jobject nat2 = env->CallObjectMethod(stereo, getNeighbor, 2);
        jobject nat3 = env->CallObjectMethod(stereo, getNeighbor, 3);
        
        istereo.central_atom = env->CallIntMethod(input, getAtomIndex, cat);
        istereo.neighbor[0] = env->CallIntMethod(input, getAtomIndex, nat0);
        istereo.neighbor[1] = env->CallIntMethod(input, getAtomIndex, nat1);
        istereo.neighbor[2] = env->CallIntMethod(input, getAtomIndex, nat2);
        istereo.neighbor[3] = env->CallIntMethod(input, getAtomIndex, nat3);

        istereo.type = env->CallIntMethod(stereo, getStereoType);
        istereo.parity = env->CallIntMethod(stereo, getParity);
        
    }
    
    tagINCHI_Input inchi_input;
    memset(&inchi_input,0,sizeof(tagINCHI_Input));
    
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
    
    inchi_input.atom = atoms;
    inchi_input.stereo0D = stereos;
    
    
    tagINCHI_Output inchi_output;
    memset(&inchi_output,0,sizeof(tagINCHI_Output));
    
    #ifdef DEBUG
    cerr << "generating InChI...\n";
    #endif
    
    int ret = GetStdINCHI(&inchi_input, &inchi_output);
    
    jobject output = getInchiOutput(env, ret, inchi_output);
    
    delete[] opts;
    FreeINCHI(&inchi_output);
    Free_inchi_Input(&inchi_input);

    return output;
}



// === INCHI to INCHI === //

/**
 * Generates InChI from InChI string.
 * @param inchi		InChI string
 * @param options	Options
 * @return		Return status.
 */
JNIEXPORT jobject JNICALL Java_net_sf_jniinchi_JniInchiWrapper_GetINCHIfromINCHI
    (JNIEnv *env, jobject, jstring inchi, jstring options) {


    tagINCHI_InputINCHI inchi_input = getInchiInputINCHI(env, inchi, options);

    tagINCHI_Output inchi_output;
    memset(&inchi_output, 0, sizeof(tagINCHI_Output));
    
    int ret = GetINCHIfromINCHI(&inchi_input, &inchi_output);
    
    jobject output = getInchiOutput(env, ret, inchi_output);
    FreeINCHI(&inchi_output);
    
    return output;
        
}



// === INCHI to STRUCTURE === //  
  
JNIEXPORT jobject Java_net_sf_jniinchi_JniInchiWrapper_GetStructFromStdINCHI
    (JNIEnv *env, jobject, jstring inchi, jstring options) {

    inchi_InputINCHI inchi_input = getInchiInputINCHI(env, inchi, options);
    
    inchi_OutputStruct inchi_output;
    memset(&inchi_output,0,sizeof(inchi_OutputStruct));
    
    
    int ret = GetStructFromStdINCHI(&inchi_input, &inchi_output);
    
    #ifdef DEBUG
    cerr << "ret: " << ret << "\n";
    #endif


    
    jobject output = env->NewObject(jniInchiOutputStructure, initJniInchiOutputStructure,
            ret,
            env->NewStringUTF(inchi_output.szMessage),
            env->NewStringUTF(inchi_output.szLog),
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
                
                // Bonds get recorded twice, so only pick one direction...
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

  
  



// === InChI KEY === //

/**
 * Generates InChI KEY from inchi.
 */
JNIEXPORT jobject JNICALL Java_net_sf_jniinchi_JniInchiWrapper_GetStdINCHIKeyFromINCHI
    (JNIEnv *env, jobject obj, jstring inchi) {
        
    const char *inchiString = env->GetStringUTFChars(inchi, 0);
    
    #ifdef DEBUG        
    cerr << "inchi: " << inchi << "\n";
    #endif        
    
    char *inchiKey = new char[26];
    int ret = GetStdINCHIKeyFromINCHI(inchiString, inchiKey);
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
