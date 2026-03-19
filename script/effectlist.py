import json


def main():
    with open("../src/main/resources/assets/anarchium/lang/en_us.json", "r") as f:
        content = json.load(f)
        effects = []

        for key in content:
            effects.append(content[key])

        effects.sort()

        for effect in effects:
            print(" *", effect)

if __name__ == "__main__":
    main()
