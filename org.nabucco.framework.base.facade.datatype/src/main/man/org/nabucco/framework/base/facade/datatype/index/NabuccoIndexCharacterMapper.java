/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco-source.org/nabucco-license.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.base.facade.datatype.index;

/**
 * NabuccoIndexCharacterMapper
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
class NabuccoIndexCharacterMapper {

    static final int SIZE = 60;

    /**
     * Map the given character to its appropriate index.
     * 
     * @param character
     *            the character to map to an index
     * 
     * @return the index
     */
    public static int getIndex(char character) {

        character = Character.toLowerCase(character);

        switch (character) {

        case 'a':
            return 0;
        case 'b':
            return 1;
        case 'c':
            return 2;
        case 'd':
            return 3;
        case 'e':
            return 4;
        case 'é':
            return 4;
        case 'è':
            return 4;
        case 'ê':
            return 4;
        case 'f':
            return 5;
        case 'g':
            return 6;
        case 'h':
            return 7;
        case 'i':
            return 8;
        case 'j':
            return 9;
        case 'k':
            return 10;
        case 'l':
            return 11;
        case 'm':
            return 12;
        case 'n':
            return 13;
        case 'o':
            return 14;
        case 'p':
            return 15;
        case 'q':
            return 16;
        case 'r':
            return 17;
        case 's':
            return 18;
        case 't':
            return 19;
        case 'u':
            return 20;
        case 'v':
            return 21;
        case 'w':
            return 22;
        case 'x':
            return 23;
        case 'y':
            return 24;
        case 'z':
            return 25;
        case '0':
            return 26;
        case '1':
            return 27;
        case '2':
            return 28;
        case '3':
            return 29;
        case '4':
            return 30;
        case '5':
            return 31;
        case '6':
            return 32;
        case '7':
            return 33;
        case '8':
            return 34;
        case '9':
            return 35;
        case 'ä':
            return 36;
        case 'ö':
            return 37;
        case 'ü':
            return 38;
        case 'ß':
            return 39;
        case ' ':
            return 40;
        case '-':
            return 41;
        case '_':
            return 42;
        case '/':
            return 43;
        case '\\':
            return 44;
        case 'ø':
            return 45;
        case ';':
            return 46;
        case ',':
            return 47;
        case '(':
            return 48;
        case ')':
            return 49;
        case '.':
            return 50;
        case ':':
            return 51;
        case '$':
            return 52;
        case '<':
            return 53;
        case '>':
            return 54;
        case '?':
            return 55;
        case '!':
            return 56;
        case ']':
            return 57;
        case '[':
            return 58;
        }

        return 59;
    }

}
