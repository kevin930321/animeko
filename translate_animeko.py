import os
import re
import opencc

def translate_sc_to_tc(directory):
    cc = opencc.OpenCC('s2t')
    # Regex to match double-quoted strings containing at least one Chinese character.
    # It accounts for escaped quotes within the string (simple handling).
    # We are looking for " ... " where ... contains Chinese.
    # [^"\\] means any character except " or \
    # \\. means any escaped character
    # The regex below simplifies to: match " followed by any number of (non-quote OR escaped char), containing at least one Chinese char, followed by "
    # But for simplicity and robustness in this specific codebase, we'll stick to the simpler version which handles most cases without getting stuck in catastrophic backtracking.
    # string_pattern = re.compile(r'"([^"\n\\]*(?:\\.[^"\n\\]*)*[\u4e00-\u9fa5]+[^"\n\\]*(?:\\.[^"\n\\]*)*)"')
    
    # Let's use a simpler one that worked in previous search logic but slightly more robust for python re
    string_pattern = re.compile(r'"([^"\n]*[\u4e00-\u9fa5]+[^"\n]*)"')

    for root, dirs, files in os.walk(directory):
        # Exclude build directories and hidden directories
        if 'build' in root.split(os.sep) or '.git' in root.split(os.sep) or '.idea' in root.split(os.sep):
            continue
            
        for file in files:
            if file.endswith(".kt"):
                file_path = os.path.join(root, file)
                
                try:
                    with open(file_path, 'r', encoding='utf-8') as f:
                        content = f.read()
                except Exception as e:
                    print(f"Error reading {file_path}: {e}")
                    continue

                def replace_match(match):
                    original_string = match.group(1)
                    converted_string = cc.convert(original_string)
                    # Reconstruct the string with quotes
                    return f'"{converted_string}"'

                new_content, count = string_pattern.subn(replace_match, content)

                if count > 0:
                    print(f"Translating {file_path} ({count} strings)...")
                    try:
                        with open(file_path, 'w', encoding='utf-8') as f:
                            f.write(new_content)
                    except Exception as e:
                        print(f"Error writing {file_path}: {e}")

if __name__ == "__main__":
    target_dir = os.getcwd()
    print(f"Scanning directory: {target_dir}")
    translate_sc_to_tc(target_dir)
