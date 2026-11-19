# Experiment 13: Password Auditing Using John the Ripper

## Aim

To perform password auditing using John the Ripper and evaluate password strength by identifying weak passwords through dictionary-based password cracking.

## Objectives

1. Understand password security and authentication.
2. Learn how John the Ripper works.
3. Perform password auditing using dictionary attacks.
4. Identify weak passwords.
5. Recommend techniques for creating strong passwords.

## Tools and Environment

- macOS on Apple Silicon
- Terminal
- Homebrew
- John the Ripper Jumbo 1.9.0
- Sample MD5 password hashes
- RockYou wordlist (`rockyou.txt`)

> **Scope:** This practical uses only three locally generated sample passwords and their hashes. No real user accounts or credentials were tested.

## Installation

Homebrew was already installed. The standard Homebrew `john` package did not recognize the `Raw-MD5` format in this environment, so it was replaced with the Jumbo build:

```bash
brew uninstall john
brew install john-jumbo
```

The supported hash formats were checked with:

```bash
john --list=formats | grep -i md5
```

The output included `Raw-MD5`, confirming that the required format was available.

## Procedure

### 1. Create the experiment directory

```bash
mkdir -p ~/Desktop/CryptoGithutPractical/Experiment13
cd ~/Desktop/CryptoGithutPractical/Experiment13
```

### 2. Generate sample MD5 hashes

The following sample passwords were used:

```bash
echo -n "password123" | md5
echo -n "admin123" | md5
echo -n "Cyber@2026" | md5
```

The generated hashes were:

| Username | Sample password | MD5 hash |
|---|---|---|
| user1 | `password123` | `482c811da5d5b4bc6d497ffa98491e38` |
| user2 | `admin123` | `0192023a7bbd73250516f069df18b500` |
| user3 | `Cyber@2026` | `5fa55c6901f19e8129bbe67fdd07406a` |

### 3. Create the hash file

The hashes were saved in `hashes.txt` using the username-and-hash format:

```text
user1:482c811da5d5b4bc6d497ffa98491e38
user2:0192023a7bbd73250516f069df18b500
user3:5fa55c6901f19e8129bbe67fdd07406a
```

To inspect the file:

```bash
cat hashes.txt
```

### 4. Perform the initial password audit

```bash
john --format=Raw-MD5 hashes.txt
```

John loaded all three hashes. The initial run tried its default single, wordlist, and incremental modes. The incremental attempt was manually stopped, so this initial run was not treated as a completed exhaustive audit.

The recovered password at this stage was checked with:

```bash
john --show --format=Raw-MD5 hashes.txt
```

At that point, `admin123` was recovered for `user2`.

### 5. Download and extract the RockYou wordlist

The RockYou archive was downloaded from the SecLists repository:

```bash
curl -L -o rockyou.txt.tar.gz https://raw.githubusercontent.com/danielmiessler/SecLists/master/Passwords/Leaked-Databases/rockyou.txt.tar.gz
```

Extract the archive:

```bash
tar -xzf rockyou.txt.tar.gz
```

Verify the extracted file:

```bash
ls -lh rockyou.txt
```

The extracted `rockyou.txt` file was approximately 133 MB in this run.

### 6. Perform the dictionary-based audit

```bash
john --wordlist=rockyou.txt --format=Raw-MD5 hashes.txt
```

John loaded the three sample hashes and found `password123` for `user1` in the RockYou wordlist. The dictionary session completed successfully.

### 7. Display the final recovered passwords

```bash
john --show --format=Raw-MD5 hashes.txt
```

Final observed output:

```text
user1:password123
user2:admin123

2 password hashes cracked, 1 left
```

## Screenshots

The screenshots below are arranged in the order of the practical procedure.

| Screenshot | Description |
|---|---|
| [01 - MD5 Hash Generation and Hash File](Screenshots/01_MD5_Hash_Generation_and_Hashes_File.png) | Generation of the three MD5 hashes and contents of `hashes.txt`. |
| [02 - Initial John the Ripper Audit](Screenshots/02_Initial_John_the_Ripper_Audit.png) | John loading the three hashes and running its initial audit modes. |
| [03 - Initial Cracked Password](Screenshots/03_Initial_Cracked_Password_admin123.png) | Initial `--show` output identifying `admin123` for `user2`. |
| [04 - RockYou Wordlist](Screenshots/04_RockYou_Wordlist_Download_and_Extraction.png) | Download, extraction, and file-size verification of `rockyou.txt`. |
| [05 - Dictionary Attack](Screenshots/05_RockYou_Dictionary_Attack.png) | RockYou dictionary attack recovering `password123` for `user1`. |
| [06 - Final Recovered Passwords](Screenshots/06_Final_Recovered_Passwords.png) | Final `--show` output reporting two cracked hashes and one remaining. |

## Observation Table

The following table records the actual results from this execution:

| S. No. | Password | Dictionary attack result | Strength assessment |
|---:|---|---|---|
| 1 | `123456` | Not tested | Very Weak |
| 2 | `password123` | Cracked | Weak |
| 3 | `admin123` | Cracked during the initial audit | Weak |
| 4 | `Cyber@2026` | Not cracked in the performed run | Moderate (as classified in the lab manual) |
| 5 | `T9#mQ2$vLp8!` | Not tested | Strong |

**Interpretation:** Two of the three generated sample hashes were recovered. `Cyber@2026` was not recovered in the attacks performed. This does not prove that it is impossible to crack; it means only that it was not found during this practical's runs. The passwords `123456` and `T9#mQ2$vLp8!` were included only in the manual's strength-evaluation examples and were not generated or tested.

## Result

The experiment demonstrated password auditing using John the Ripper. Three sample MD5 password hashes were generated and tested. The initial audit recovered `admin123`, and the RockYou dictionary attack recovered `password123`. The final report showed **2 password hashes cracked and 1 remaining**. The practical illustrated that common and predictable passwords are more likely to be found through dictionary-based attacks, while a password not found by a particular wordlist run is not necessarily immune to other attacks.

## Precautions

1. Perform password auditing only on systems and password hashes for which authorization has been granted.
2. Never use real user passwords for classroom demonstrations.
3. Use sample password hashes for practical experiments.
4. Avoid storing passwords in plaintext.
5. Prefer secure password hashing algorithms such as Argon2id, bcrypt, or scrypt instead of MD5.

## Security Note

MD5 is used here only because the lab manual specifies it for a simple demonstration. MD5 is fast and unsuitable for storing real passwords. Real applications should use a dedicated password-hashing algorithm such as Argon2id, bcrypt, or scrypt, with appropriate parameters and unique salts.

## Repository Contents

```text
Experiment13/
├── README.md
├── hashes.txt
└── Screenshots/
    ├── 01_MD5_Hash_Generation_and_Hashes_File.png
    ├── 02_Initial_John_the_Ripper_Audit.png
    ├── 03_Initial_Cracked_Password_admin123.png
    ├── 04_RockYou_Wordlist_Download_and_Extraction.png
    ├── 05_RockYou_Dictionary_Attack.png
    └── 06_Final_Recovered_Passwords.png
```

The RockYou wordlist and its compressed archive are intentionally not included in this repository package because they are large files. Download and extract the wordlist separately when reproducing the dictionary attack.
