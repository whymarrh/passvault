# Passvault #

Passvault is a very simple, almost rudimentary, password manager and password generator. It uses Java's security and cryptography APIs to encrypt and store the list of passwords to disk.

You can start using Passvault by simply compiling and running the main `Passvault` class. There exist no external dependencies.

**You can ignore the following compiler warning:**

```
./PasswordList.java:89: warning: [unchecked] unchecked cast
found   : java.lang.Object
required: java.util.HashMap<java.lang.String,java.lang.String>
    pwdList = (HashMap<String, String>) ois.readObject();
                                                      ^
1 warning
```

**But there is a fatal insecurity in this program:**  
The master password can be changed or removed by overwriting or deleting the `.pwd` file in the installation directory. If this happens, all the passwords that were previously saved will still remain, and can/will be seen by others.

**After compilation:**  
Once the main class has been compiled, all the required files will now reside in the current directory, and future launches of the program will need to be done from the same directory.

On the first launch of the program, you will be prompted to create a master password (Figure 1). This will be used to authenticate the user on subsequent launches of the program (Figure 2).

To create a new password entry, select Add Password from the File menu, enter the name of the service, and generate a random secure password (Figure 3). This new password will be added to the list on the right side (Figure 4). To view the password, double-click on the entry in the list; the password will be displayed in large visible-to-everybody-in-the-room print (Figure 5).

As of yet, there is no option to delete a password.

**Figures 1-5 below:**

![Figure 1][1]
![Figure 2][2]
![Figure 3][3]
![Figure 4][4]
![Figure 5][5]

**Credit**  
Credit to [XKCD][6] for the two comics to which I have linked, and to [Flat-it](http://flat-it.com/) for the Bebas typeface.

**License:**  
Everyone is permitted to copy and distribute verbatim or modified copies of this software and associated documentation files, and changing it is allowed as long as the name is changed.

This software is provided "as is", without warranty of any kind, express or implied, including but not limited to the warranties of merchantability, fitness for a particular purpose and non-infringement.

  [1]:https://github.com/whymarrh/passvault/raw/master/docs/f1.png
  [2]:https://github.com/whymarrh/passvault/raw/master/docs/f2.png
  [3]:https://github.com/whymarrh/passvault/raw/master/docs/f3.png
  [4]:https://github.com/whymarrh/passvault/raw/master/docs/f4.png
  [5]:https://github.com/whymarrh/passvault/raw/master/docs/f5.png
  [6]:http://xkcd.com
