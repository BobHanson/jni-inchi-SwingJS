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
#include <string.h>
#include <stdio.h>
#include <stdlib.h>

#include "net_sf_jniinchi_JniInchiWrapper.h"
#include <inchi_api.h>
#include <ichisize.h>

#define NATIVE_LIB_VERSION "1.03_1"

/*
Uncomment to enable debug mode
#define DEBUG 1
*/


/* === CLASS REFs === */

jclass IllegalArgumentException;

jclass jniInchiInput, jniInchiOutput, jniInchiOutputKey, jniInchiOutputStructure;
jclass jniInchiStructure, jniInchiAtom, jniInchiBond, jniInchiStereo0D;

/* === METHOD REFs === */

/* constructors */
jmethodID initJniInchiOutput, initJniInchiOutputKey;
jmethodID initJniInchiAtom, initJniInchiBond, initJniInchiStereo0D;
jmethodID initJniInchiOutputStructure;

/* structure methods */
jmethodID getNumAtoms, getNumBonds, getNumStereo0D, getOptions, getAtom, getAtomIndex, getBond, getStereo0D;
jmethodID addAtom, addBond, addStereo0D;

/* atom methods */
jmethodID getElementType, getX, getY, getZ, getCharge, getRadical, getImplicitH, getImplicitProtium, getImplicitDeuterium, getImplicitTritium, getIsotopicMass;
jmethodID setCharge, setRadical, setImplicitH, setImplicitProtium, setImplicitDeuterium, setImplicitTritium, setIsotopicMass;

/* bond methods */
jmethodID getOriginAtom, getTargetAtom, getBondType, getBondStereo;

/* stereo0D methods */
jmethodID getCentralAtom, getNeighbor, getStereoType, getParity;




/* === CHECK NATIVE CODE VERSION === */

JNIEXPORT jstring JNICALL Java_net_sf_jniinchi_JniInchiWrapper_LibInchiGetVersion
    (JNIEnv *env, jclass class) {

    return (*env)->NewStringUTF(env, NATIVE_LIB_VERSION);

}


/* === JNI INITIALIZERS === */


/*
 Initialises the jclass pointer clazz to point to the named class
*/
int initClass(JNIEnv *env, jclass *clazz, char *name) {

    if (NULL != (*clazz)) {
        return 1;
    }

    jclass clz = (*env)->FindClass(env, name);
    if (clz == NULL) {
        return 0;
    }

    (*clazz) = (jclass) (*env)->NewGlobalRef(env, clz);
    if ((*clazz) == NULL) {
        return 0;
    }

    return 1;

}


int initClassRefs(JNIEnv *env) {

    if (!initClass(env, &IllegalArgumentException, "java/lang/IllegalArgumentException")) return 0;

    if (!initClass(env, &jniInchiInput, "net/sf/jniinchi/JniInchiInput")) return 0;
    if (!initClass(env, &jniInchiOutput, "net/sf/jniinchi/JniInchiOutput")) return 0;
    if (!initClass(env, &jniInchiOutputStructure, "net/sf/jniinchi/JniInchiOutputStructure")) return 0;
    if (!initClass(env, &jniInchiOutputKey, "net/sf/jniinchi/JniInchiOutputKey")) return 0;

    if (!initClass(env, &jniInchiStructure, "net/sf/jniinchi/JniInchiStructure")) return 0;
    if (!initClass(env, &jniInchiAtom, "net/sf/jniinchi/JniInchiAtom")) return 0;
    if (!initClass(env, &jniInchiBond, "net/sf/jniinchi/JniInchiBond")) return 0;
    if (!initClass(env, &jniInchiStereo0D, "net/sf/jniinchi/JniInchiStereo0D")) return 0;

    return 1;

}


int initMethodRefs(JNIEnv *env) {

    if (0== (getNumAtoms = (*env)->GetMethodID(env, jniInchiStructure, "getNumAtoms", "()I"))) return 0;
    if (0== (getNumBonds = (*env)->GetMethodID(env, jniInchiStructure, "getNumBonds", "()I"))) return 0;
    if (0== (getNumStereo0D = (*env)->GetMethodID(env, jniInchiStructure, "getNumStereo0D", "()I"))) return 0;
    if (0== (getOptions = (*env)->GetMethodID(env, jniInchiInput, "getOptions", "()Ljava/lang/String;"))) return 0;
    if (0== (getAtom = (*env)->GetMethodID(env, jniInchiStructure, "getAtom", "(I)Lnet/sf/jniinchi/JniInchiAtom;"))) return 0;
    if (0== (getBond = (*env)->GetMethodID(env, jniInchiStructure, "getBond", "(I)Lnet/sf/jniinchi/JniInchiBond;"))) return 0;
    if (0== (getStereo0D = (*env)->GetMethodID(env, jniInchiStructure, "getStereo0D", "(I)Lnet/sf/jniinchi/JniInchiStereo0D;"))) return 0;
    if (0== (getAtomIndex = (*env)->GetMethodID(env, jniInchiStructure, "getAtomIndex", "(Lnet/sf/jniinchi/JniInchiAtom;)I"))) return 0;

    if (0== (addAtom = (*env)->GetMethodID(env, jniInchiStructure, "addAtom", "(Lnet/sf/jniinchi/JniInchiAtom;)Lnet/sf/jniinchi/JniInchiAtom;"))) return 0;
    if (0== (addBond = (*env)->GetMethodID(env, jniInchiStructure, "addBond", "(Lnet/sf/jniinchi/JniInchiBond;)Lnet/sf/jniinchi/JniInchiBond;"))) return 0;
    if (0== (addStereo0D = (*env)->GetMethodID(env, jniInchiStructure, "addStereo0D", "(Lnet/sf/jniinchi/JniInchiStereo0D;)Lnet/sf/jniinchi/JniInchiStereo0D;"))) return 0;

    if (0== (initJniInchiAtom = (*env)->GetMethodID(env, jniInchiAtom, "<init>", "(DDDLjava/lang/String;)V"))) return 0;
    if (0== (initJniInchiBond = (*env)->GetMethodID(env, jniInchiBond, "<init>", "(Lnet/sf/jniinchi/JniInchiAtom;Lnet/sf/jniinchi/JniInchiAtom;II)V"))) return 0;
    if (0== (initJniInchiStereo0D = (*env)->GetMethodID(env, jniInchiStereo0D, "<init>", "(Lnet/sf/jniinchi/JniInchiAtom;Lnet/sf/jniinchi/JniInchiAtom;Lnet/sf/jniinchi/JniInchiAtom;Lnet/sf/jniinchi/JniInchiAtom;Lnet/sf/jniinchi/JniInchiAtom;II)V"))) return 0;

    if (0== (getElementType = (*env)->GetMethodID(env, jniInchiAtom, "getElementType", "()Ljava/lang/String;"))) return 0;
    if (0== (getX = (*env)->GetMethodID(env, jniInchiAtom, "getX", "()D"))) return 0;
    if (0== (getY = (*env)->GetMethodID(env, jniInchiAtom, "getY", "()D"))) return 0;
    if (0== (getZ = (*env)->GetMethodID(env, jniInchiAtom, "getZ", "()D"))) return 0;
    if (0== (getCharge = (*env)->GetMethodID(env, jniInchiAtom, "getCharge", "()I"))) return 0;
    if (0== (getRadical = (*env)->GetMethodID(env, jniInchiAtom, "getInchiRadical", "()I"))) return 0;
    if (0== (getImplicitH = (*env)->GetMethodID(env, jniInchiAtom, "getImplicitH", "()I"))) return 0;
    if (0== (getImplicitProtium = (*env)->GetMethodID(env, jniInchiAtom, "getImplicitProtium", "()I"))) return 0;
    if (0== (getImplicitDeuterium = (*env)->GetMethodID(env, jniInchiAtom, "getImplicitDeuterium", "()I"))) return 0;
    if (0== (getImplicitTritium = (*env)->GetMethodID(env, jniInchiAtom, "getImplicitTritium", "()I"))) return 0;
    if (0== (getIsotopicMass = (*env)->GetMethodID(env, jniInchiAtom, "getIsotopicMass", "()I"))) return 0;

    if (0== (setCharge = (*env)->GetMethodID(env, jniInchiAtom, "setCharge", "(I)V"))) return 0;
    if (0== (setRadical = (*env)->GetMethodID(env, jniInchiAtom, "setInchiRadical", "(I)V"))) return 0;
    if (0== (setImplicitH = (*env)->GetMethodID(env, jniInchiAtom, "setImplicitH", "(I)V"))) return 0;
    if (0== (setImplicitProtium = (*env)->GetMethodID(env, jniInchiAtom, "setImplicitProtium", "(I)V"))) return 0;
    if (0== (setImplicitDeuterium = (*env)->GetMethodID(env, jniInchiAtom, "setImplicitDeuterium", "(I)V"))) return 0;
    if (0== (setImplicitTritium = (*env)->GetMethodID(env, jniInchiAtom, "setImplicitTritium", "(I)V"))) return 0;
    if (0== (setIsotopicMass = (*env)->GetMethodID(env, jniInchiAtom, "setIsotopicMass", "(I)V"))) return 0;

    if (0== (getOriginAtom = (*env)->GetMethodID(env, jniInchiBond, "getOriginAtom", "()Lnet/sf/jniinchi/JniInchiAtom;"))) return 0;
    if (0== (getTargetAtom = (*env)->GetMethodID(env, jniInchiBond, "getTargetAtom", "()Lnet/sf/jniinchi/JniInchiAtom;"))) return 0;
    if (0== (getBondType = (*env)->GetMethodID(env, jniInchiBond, "getInchiBondType", "()I"))) return 0;
    if (0== (getBondStereo = (*env)->GetMethodID(env, jniInchiBond, "getInchiBondStereo", "()I"))) return 0;

    if (0== (getCentralAtom = (*env)->GetMethodID(env, jniInchiStereo0D, "getCentralAtom", "()Lnet/sf/jniinchi/JniInchiAtom;"))) return 0;
    if (0== (getNeighbor = (*env)->GetMethodID(env, jniInchiStereo0D, "getNeighbor", "(I)Lnet/sf/jniinchi/JniInchiAtom;"))) return 0;
    if (0== (getStereoType = (*env)->GetMethodID(env, jniInchiStereo0D, "getInchiStereoType", "()I"))) return 0;
    if (0== (getParity = (*env)->GetMethodID(env, jniInchiStereo0D, "getInchiParity", "()I"))) return 0;

    if (0== (initJniInchiOutput = (*env)->GetMethodID(env, jniInchiOutput, "<init>", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V"))) return 0;


    if (0== (initJniInchiOutputStructure = (*env)->GetMethodID(env, jniInchiOutputStructure, "<init>", "(ILjava/lang/String;Ljava/lang/String;JJJJ)V"))) return 0;

    if (0== (initJniInchiOutputKey = (*env)->GetMethodID(env, jniInchiOutputKey, "<init>", "(ILjava/lang/String;)V"))) return 0;

    return 1;
}



JNIEXPORT void JNICALL Java_net_sf_jniinchi_JniInchiWrapper_init
    (JNIEnv *env, jobject obj) {

    fprintf(stderr, "Init JniInchi\n");

    if (0 == initClassRefs(env)) {
        return;
    }
    initMethodRefs(env);

}





inchi_InputINCHI* getInchiInputINCHI(JNIEnv *env, jstring inchi, jstring options) {

    /* Convert java.lang.String inchi to char* */
    const char *inchiString = (*env)->GetStringUTFChars(env, inchi, 0);
    char *szInchi = malloc(sizeof(char) * (strlen(inchiString)+1));
    strcpy(szInchi, inchiString);
    (*env)->ReleaseStringUTFChars(env, inchi, inchiString);

    /* Convert java.lang.String options to char* */
    const char *optionsString = (*env)->GetStringUTFChars(env, options, 0);
    char *szOptions = malloc(sizeof(char) * (strlen(optionsString)+1));
    strcpy(szOptions, optionsString);
    (*env)->ReleaseStringUTFChars(env, options, optionsString);

    #ifdef DEBUG
    fprintf(stderr, "inchi: %s\n", szInchi);
    fprintf(stderr, "options: %s\n", szOptions);
    #endif

    inchi_InputINCHI *input;                      /* Allocate memory */
    input = malloc(sizeof(inchi_InputINCHI));
    memset(input, 0, sizeof(inchi_InputINCHI));  /* Set initial values to 0 */

    (*input).szInChI = szInchi;
    (*input).szOptions = szOptions;

    return input;

}



jobject getInchiOutput(JNIEnv *env, int ret, inchi_Output inchi_output) {

    #ifdef DEBUG
    fprintf(stderr, "ret: %d\n", ret);
    fprintf(stderr, "message: %s\n", inchi_output.szMessage);
    fprintf(stderr, "inchi: %s\n", inchi_output.szInChI);
    #endif

    jobject output = (*env)->NewObject(env, jniInchiOutput, initJniInchiOutput,
            ret,
            (*env)->NewStringUTF(env, inchi_output.szInChI),
            (*env)->NewStringUTF(env, inchi_output.szAuxInfo),
            (*env)->NewStringUTF(env, inchi_output.szMessage),
            (*env)->NewStringUTF(env, inchi_output.szLog));

    return output;

}


inchi_Input* getInchiInput(JNIEnv *env, jobject input) {

    int i;

    #ifdef DEBUG
    fprintf(stderr, "** Get InChI\n");
    #endif

    jboolean iscopy = JNI_TRUE;

    jint natoms = (*env)->CallIntMethod(env, input, getNumAtoms);
    jint nstereo = (*env)->CallIntMethod(env, input, getNumStereo0D);
    jint nbonds = (*env)->CallIntMethod(env, input, getNumBonds);

    #ifdef DEBUG
    fprintf(stderr, "natoms: %d\n", natoms);
    fprintf(stderr, "nbonds: %d\n", nbonds);
    fprintf(stderr, "nstereo: %d\n", nstereo);
    #endif

/*    fprintf(stderr, "natoms: %d\n", natoms); */
/*    fprintf(stderr, "maxatoms: %d\n", MAX_ATOMS); */

    if (natoms > MAX_ATOMS) {
        (*env)->ThrowNew(env, IllegalArgumentException, "Too many atoms");
        return 0;
    }


    inchi_Atom *atoms = malloc(sizeof(inchi_Atom) * natoms);
    memset(atoms,0,sizeof(inchi_Atom) * natoms);

    for (i = 0; i < natoms; i++) {

        #ifdef DEBUG
        fprintf(stderr, "atom #%d:", i);
        #endif

        jobject atom = (*env)->CallObjectMethod(env, input, getAtom, i);

        inchi_Atom *iatom = &atoms[i];

        jstring jelname = (jstring) (*env)->CallObjectMethod(env, atom, getElementType);
        const char *elname = (*env)->GetStringUTFChars(env, jelname, &iscopy);
        if (strlen(elname) > ATOM_EL_LEN) {
            (*env)->ThrowNew(env, IllegalArgumentException, "Element name too long; maximum: " + ATOM_EL_LEN);
            (*env)->ReleaseStringUTFChars(env, jelname, elname);
            return 0;
        }
        strcpy(iatom->elname, elname);
        (*env)->ReleaseStringUTFChars(env, jelname, elname);

        #ifdef DEBUG
        fprintf(stderr, " %s", iatom->elname);
        #endif

        iatom->x = (*env)->CallDoubleMethod(env, atom, getX);
        iatom->y = (*env)->CallDoubleMethod(env, atom, getY);
        iatom->z = (*env)->CallDoubleMethod(env, atom, getZ);

        #ifdef DEBUG
        fprintf(stderr, " [%f,%f,%f]", iatom->x, iatom->y, iatom->z);
        #endif

        iatom->charge = (*env)->CallIntMethod(env, atom, getCharge);
        iatom->radical = (*env)->CallIntMethod(env, atom, getRadical);

        iatom->num_iso_H[0] = (*env)->CallIntMethod(env, atom, getImplicitH);
        iatom->num_iso_H[1] = (*env)->CallIntMethod(env, atom, getImplicitProtium);
        iatom->num_iso_H[2] = (*env)->CallIntMethod(env, atom, getImplicitDeuterium);
        iatom->num_iso_H[3] = (*env)->CallIntMethod(env, atom, getImplicitTritium);

        iatom->isotopic_mass = (*env)->CallIntMethod(env, atom, getIsotopicMass);

        iatom->num_bonds = 0;

        #ifdef DEBUG
        fprintf(stderr, "\n");
        #endif
    }


    for (i = 0; i < nbonds; i++) {

        jobject bond = (*env)->CallObjectMethod(env, input, getBond, i);
        jobject atomO = (*env)->CallObjectMethod(env, bond, getOriginAtom);
        jobject atomT = (*env)->CallObjectMethod(env, bond, getTargetAtom);
        jint bondType = (*env)->CallIntMethod(env, bond, getBondType);
        jint bondStereo = (*env)->CallIntMethod(env, bond, getBondStereo);

        jint iaO = (*env)->CallIntMethod(env, input, getAtomIndex, atomO);
        jint iaT = (*env)->CallIntMethod(env, input, getAtomIndex, atomT);


        #ifdef DEBUG
        fprintf(stderr, "bond#%d: a#%d,a#%d (%d)\n", i, iaO, iaT, bondType);
        #endif

        inchi_Atom *iatom = &atoms[iaO];
        int numbonds = iatom->num_bonds;
        if (numbonds >= MAXVAL) {
            free(atoms);
            (*env)->ThrowNew(env, IllegalArgumentException, "Too many bonds from one atom; maximum: " + MAXVAL);
            return 0;
        }
        iatom->neighbor[numbonds] = iaT;
        iatom->bond_type[numbonds] = bondType;
        iatom->bond_stereo[numbonds] = bondStereo;
        iatom->num_bonds++;

    }



    inchi_Stereo0D *stereos = malloc(sizeof(inchi_Stereo0D) * nstereo);
    memset(stereos,0,sizeof(inchi_Stereo0D) * nstereo);

    for (i = 0; i < nstereo; i++) {

        jobject stereo = (*env)->CallObjectMethod(env, input, getStereo0D, i);

        inchi_Stereo0D *istereo = &stereos[i];

        jobject cat = (*env)->CallObjectMethod(env, stereo, getCentralAtom);
        jobject nat0 = (*env)->CallObjectMethod(env, stereo, getNeighbor, 0);
        jobject nat1 = (*env)->CallObjectMethod(env, stereo, getNeighbor, 1);
        jobject nat2 = (*env)->CallObjectMethod(env, stereo, getNeighbor, 2);
        jobject nat3 = (*env)->CallObjectMethod(env, stereo, getNeighbor, 3);

        istereo->central_atom = (*env)->CallIntMethod(env, input, getAtomIndex, cat);
        istereo->neighbor[0] = (*env)->CallIntMethod(env, input, getAtomIndex, nat0);
        istereo->neighbor[1] = (*env)->CallIntMethod(env, input, getAtomIndex, nat1);
        istereo->neighbor[2] = (*env)->CallIntMethod(env, input, getAtomIndex, nat2);
        istereo->neighbor[3] = (*env)->CallIntMethod(env, input, getAtomIndex, nat3);

        istereo->type = (*env)->CallIntMethod(env, stereo, getStereoType);
        istereo->parity = (*env)->CallIntMethod(env, stereo, getParity);

    }

    inchi_Input *inchi_input;                      /* Allocate memory */
    inchi_input = malloc(sizeof(inchi_Input));
    memset(inchi_input, 0, sizeof(inchi_Input));

    jstring joptions = (jstring) (*env)->CallObjectMethod(env, input, getOptions);
    const char *options = (*env)->GetStringUTFChars(env, joptions, &iscopy);
    char *opts = malloc(sizeof(char) * (strlen(options)+1));
    strcpy(opts, options);

    #ifdef DEBUG
/*    cerr << "options: " << opts << "\n"; */
    #endif

    (*inchi_input).szOptions = opts;
    (*env)->ReleaseStringUTFChars(env, joptions, options);


    (*inchi_input).num_atoms = natoms;
    (*inchi_input).num_stereo0D = nstereo;

    (*inchi_input).atom = atoms;
    (*inchi_input).stereo0D = stereos;


  return inchi_input;

}




/****************************************************************************
 *                                                                          *
 *   STRUCTURE to INCHI                                                     *
 *                                                                          *
 ****************************************************************************/


JNIEXPORT jobject JNICALL Java_net_sf_jniinchi_JniInchiWrapper_GetINCHI
    (JNIEnv *env, jobject obj, jobject input) {

    #ifdef DEBUG
    fprintf(stderr, "** Get InChI\n");
    #endif

    inchi_Input *inchi_input = getInchiInput(env, input);
    if (!inchi_input) {
        /* Exception was thrown */
        return 0;
    }

    inchi_Output *inchi_output;
    inchi_output = malloc(sizeof(inchi_Output));
    memset(inchi_output, 0, sizeof(inchi_Output));

    #ifdef DEBUG
    fprintf(stderr, "generating InChI\n");
    #endif

    int ret = GetINCHI(inchi_input, inchi_output);

    jobject output = getInchiOutput(env, ret, *inchi_output);

    FreeINCHI(inchi_output);
    free((*inchi_input).szOptions);
    Free_inchi_Input(inchi_input);
    free(inchi_output);
    free(inchi_input);
    
    return output;
}


JNIEXPORT jobject JNICALL Java_net_sf_jniinchi_JniInchiWrapper_GetStdINCHI
    (JNIEnv *env, jobject obj, jobject input) {

    #ifdef DEBUG
    fprintf(stderr, "** Get StdInChI\n");
    #endif

    inchi_Input *inchi_input = getInchiInput(env, input);
    if (!inchi_input) {
        /* Exception was thrown */
        return 0;
    }

    inchi_Output *inchi_output;
    inchi_output = malloc(sizeof(inchi_Output));
    memset(inchi_output, 0, sizeof(inchi_Output));

    #ifdef DEBUG
    fprintf(stderr, "generating StdInChI\n");
    #endif

    int ret = GetStdINCHI(inchi_input, inchi_output);

    jobject output = getInchiOutput(env, ret, *inchi_output);

    FreeStdINCHI(inchi_output);
    free((*inchi_input).szOptions);
    Free_std_inchi_Input(inchi_input);
    free(inchi_output);
    free(inchi_input);

    return output;
}



/****************************************************************************
 *                                                                          *
 *   STRUCTURE to INCHI KEY                                                 *
 *                                                                          *
 ****************************************************************************/

/**
 * Generates InChI KEY from InChI.
 */
JNIEXPORT jobject JNICALL Java_net_sf_jniinchi_JniInchiWrapper_GetINCHIKeyFromINCHI
    (JNIEnv *env, jobject obj, jstring inchi) {

    const char *inchiString = (*env)->GetStringUTFChars(env, inchi, 0);

    #ifdef DEBUG
    fprintf(stderr, "** Get InChIKey from InChI\n");
    fprintf(stderr, "inchi: %s\n", inchi);
    #endif

    /* The user-supplied buffer szINCHIKey should be at least 28 bytes long. */
    char *szINCHIKey = malloc(sizeof(char) * 28);
    memset(szINCHIKey, 0, sizeof(char) * 28);

    /* hash extension (up to 256 bits; 1st block) string */
    /* Caller should allocate space for 64 characters + trailing NULL */
    char *szXtra1 = malloc(sizeof(char) * 65);
    memset(szXtra1, 0, sizeof(char) * 65);

    /* hash extension (up to 256 bits; 2nd block) string */
    /* Caller should allocate space for 64 characters + trailing NULL */
    char *szXtra2 = malloc(sizeof(char) * 65);
    memset(szXtra2, 0, sizeof(char) * 65);

    #ifdef DEBUG
    fprintf(stderr, "getting InChIKey...\n");
    #endif

    /* xtra1 =1 calculate hash extension (up to 256 bits; 1st block) */
    /* xtra2 =1 calculate hash extension (up to 256 bits; 2nd block) */
    const int xtra1 = 1;
    const int xtra2 = 1;

    int ret = GetINCHIKeyFromINCHI(inchiString, xtra1, xtra2, szINCHIKey, szXtra1, szXtra2);
    (*env)->ReleaseStringUTFChars(env, inchi, inchiString);

    #ifdef DEBUG
    fprintf(stderr, "ret: %d\n", ret);
    fprintf(stderr, "key: %s\n", szINCHIKey);
    #endif

    jstring key = (*env)->NewStringUTF(env, szINCHIKey);

    free(szINCHIKey);
    free(szXtra1);
    free(szXtra2);

    jobject robj = (*env)->NewObject(env, jniInchiOutputKey, initJniInchiOutputKey, ret, key);

    #ifdef DEBUG
    fprintf(stderr, "----\n\n");
    #endif

    return robj;

}


/**
 * Generates StdInChI KEY from StdInChI.
 */
JNIEXPORT jobject JNICALL Java_net_sf_jniinchi_JniInchiWrapper_GetStdINCHIKeyFromStdINCHI
    (JNIEnv *env, jobject obj, jstring inchi) {

    const char *inchiString = (*env)->GetStringUTFChars(env, inchi, 0);

    #ifdef DEBUG
    fprintf(stderr, "** Get StdInChIKey from StdInChI\n");
    fprintf(stderr, "inchi: %s\n", inchi);
    #endif

    /* The user-supplied buffer szINCHIKey should be at least 28 bytes long. */
    char *szINCHIKey = malloc(sizeof(char) * 28);
    memset(szINCHIKey, 0, sizeof(char) * 28);

    #ifdef DEBUG
    fprintf(stderr, "generating StdInChIKey...\n");
    #endif

    int ret = GetStdINCHIKeyFromStdINCHI(inchiString, szINCHIKey);
    (*env)->ReleaseStringUTFChars(env, inchi, inchiString);

    #ifdef DEBUG
    fprintf(stderr, "ret: %d\n", ret);
    fprintf(stderr, "key: %s\n", szINCHIKey);
    #endif

    jstring key = (*env)->NewStringUTF(env, szINCHIKey);

    free(szINCHIKey);

    jobject robj = (*env)->NewObject(env, jniInchiOutputKey, initJniInchiOutputKey, ret, key);

    #ifdef DEBUG
    fprintf(stderr, "----\n\n");
    #endif

    return robj;

}





/* === INCHI to INCHI === */

/**
 * Generates InChI from InChI string.
 * @param inchi		InChI string
 * @param options	Options
 * @return		Return status.
 */
JNIEXPORT jobject JNICALL Java_net_sf_jniinchi_JniInchiWrapper_GetINCHIfromINCHI
    (JNIEnv *env, jobject obj, jstring inchi, jstring options) {

    inchi_InputINCHI *inchi_input = getInchiInputINCHI(env, inchi, options);

    inchi_Output *inchi_output;
    memset(inchi_output, 0, sizeof(inchi_Output));

    int ret = GetINCHIfromINCHI(inchi_input, inchi_output);

    jobject output = getInchiOutput(env, ret, *inchi_output);
    FreeINCHI(inchi_output);
    free(inchi_output);
    free(inchi_input);

    return output;

}



/* === INCHI to STRUCTURE === */

JNIEXPORT jobject JNICALL Java_net_sf_jniinchi_JniInchiWrapper_GetStructFromINCHI
    (JNIEnv *env, jobject obj, jstring inchi, jstring options) {

    int i, j;

    #ifdef DEBUG
    fprintf(stderr, "** Get Struct From InChI\n");
    #endif

    inchi_InputINCHI *inchi_input = getInchiInputINCHI(env, inchi, options);

    inchi_OutputStruct *inchi_output = malloc(sizeof(inchi_OutputStruct));
    memset(inchi_output,0,sizeof(inchi_OutputStruct));


    #ifdef DEBUG
    fprintf(stderr, "getting structure...\n");
    #endif

    int ret = GetStructFromINCHI(inchi_input, inchi_output);

    #ifdef DEBUG
    fprintf(stderr, "ret: %d\n", ret);
    #endif

    jobject output = (*env)->NewObject(env, jniInchiOutputStructure, initJniInchiOutputStructure,
            ret,
            (*env)->NewStringUTF(env, (*inchi_output).szMessage),
            (*env)->NewStringUTF(env, (*inchi_output).szLog),
            (*inchi_output).WarningFlags[0][0],
            (*inchi_output).WarningFlags[0][1],
            (*inchi_output).WarningFlags[1][0],
            (*inchi_output).WarningFlags[1][1]);


    int numatoms = (*inchi_output).num_atoms;
    int numstereo = (*inchi_output).num_stereo0D;


    for (i = 0; i < numatoms; i++) {

        inchi_Atom iatom = (*inchi_output).atom[i];

        #ifdef DEBUG
        fprintf(stderr, "atom #%d: %s\n", i, iatom.elname);
        #endif

        jobject atom = (*env)->NewObject(env, jniInchiAtom, initJniInchiAtom,
                iatom.x,
                iatom.y,
                iatom.z,
                (*env)->NewStringUTF(env, iatom.elname));

        (*env)->CallVoidMethod(env, atom, setCharge, iatom.charge);
        (*env)->CallVoidMethod(env, atom, setRadical, iatom.radical);
        (*env)->CallVoidMethod(env, atom, setImplicitH, iatom.num_iso_H[0]);
        (*env)->CallVoidMethod(env, atom, setImplicitProtium, iatom.num_iso_H[1]);
        (*env)->CallVoidMethod(env, atom, setImplicitDeuterium, iatom.num_iso_H[2]);
        (*env)->CallVoidMethod(env, atom, setImplicitTritium, iatom.num_iso_H[3]);
        (*env)->CallVoidMethod(env, atom, setIsotopicMass, iatom.isotopic_mass);

        (*env)->CallVoidMethod(env, output, addAtom, atom);

    }


    /* Bonds */

    for (i = 0; i < numatoms; i++) {

        inchi_Atom iatom = (*inchi_output).atom[i];
        int numbonds = iatom.num_bonds;

        if (numbonds > 0) {

            jobject atO = (*env)->CallObjectMethod(env, output, getAtom, i);

            for (j = 0; j < numbonds; j++) {

                /* Bonds get recorded twice, so only pick one direction... */
                if (iatom.neighbor[j] < i) {

                    #ifdef DEBUG
                    fprintf(stderr, "bond: a#%d,a#%d (%d)\n", i, iatom.neighbor[j], iatom.bond_type[j]);
                    #endif

                    jobject atT = (*env)->CallObjectMethod(env, output, getAtom, iatom.neighbor[j]);
                    jobject bond = (*env)->NewObject(env, jniInchiBond, initJniInchiBond, atO, atT, iatom.bond_type[j], iatom.bond_stereo[j]);
                    (*env)->CallVoidMethod(env, output, addBond, bond);

                }
            }

        }
    }


    for (i = 0; i < numstereo; i++) {

        inchi_Stereo0D istereo = (*inchi_output).stereo0D[i];

        jobject atC = NULL;
        if (istereo.central_atom != NO_ATOM) {
            atC = (*env)->CallObjectMethod(env, output, getAtom, istereo.central_atom);
        }
        jobject an0 = (*env)->CallObjectMethod(env, output, getAtom, istereo.neighbor[0]);
        jobject an1 = (*env)->CallObjectMethod(env, output, getAtom, istereo.neighbor[1]);
        jobject an2 = (*env)->CallObjectMethod(env, output, getAtom, istereo.neighbor[2]);
        jobject an3 = (*env)->CallObjectMethod(env, output, getAtom, istereo.neighbor[3]);

        jobject stereo = (*env)->NewObject(env, jniInchiStereo0D, initJniInchiStereo0D,
                    atC,
                    an0,
                    an1,
                    an2,
                    an3,
                    istereo.type,
                    istereo.parity);

        (*env)->CallVoidMethod(env, output, addStereo0D, stereo);
    }


    FreeStructFromINCHI(inchi_output);
    free(inchi_output);
    free(inchi_input);

    #ifdef DEBUG
    fprintf(stderr, "----\n\n");
    #endif

    return output;
}








/**
 * Checks InChIKey
 */
JNIEXPORT jint JNICALL Java_net_sf_jniinchi_JniInchiWrapper_CheckINCHIKey
    (JNIEnv *env, jobject obj, jstring key) {

    /* Get inchi string */
    const char *keyString = (*env)->GetStringUTFChars(env, key, 0);

    #ifdef DEBUG
    fprintf(stderr, "** Check InChIKey\n");
    fprintf(stderr, "key: %s\n", keyString);
    fprintf(stderr, "checking InChIKey...\n");
    #endif

    int ret = CheckINCHIKey(keyString);

    #ifdef DEBUG
    fprintf(stderr, "ret: %d\n", ret);
    #endif

    (*env)->ReleaseStringUTFChars(env, key, keyString);

    #ifdef DEBUG
    fprintf(stderr, "----\n\n");
    #endif

    return ret;

}
