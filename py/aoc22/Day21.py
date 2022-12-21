import sympy

monkeyNums = { "humn": sympy.Symbol("x") }

def isInteger(s):
    try:
        i = int(s)
        return True
    except:
        return False

def computeEquation(oper: str, mOne: int, mTwo: int) -> int:
    if oper == "+":
        return mOne + mTwo
    elif oper == "-":
        return mOne - mTwo
    elif oper == "*":
        return mOne * mTwo
    elif oper == "/":
        return mOne / mTwo
    else:
        raise Exception("Invalid equation operator.")

lines = [line.strip() for line in open("G:\JavaProjects\\advent-of-code\src\main\\resources\\22\day21.txt")]

for l in lines:
    monkey, eq = l.split(": ")
    if monkey in monkeyNums:
        continue

    if isInteger(eq):
        monkeyNums[monkey] = sympy.Integer(eq)
    else:
        mOne, oper, mTwo = eq.split(" ")
        if mOne in monkeyNums and mTwo in monkeyNums:
            if monkey == "root":
                print(sympy.solve(monkeyNums[mOne] - monkeyNums[mTwo]))
                break
            monkeyNums[monkey] = computeEquation(oper, monkeyNums[mOne], monkeyNums[mTwo])
        else:
            lines.append(l)
