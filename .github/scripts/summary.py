"""Publish JUnit results to the GitHub Actions run summary (standard library only)."""
import html
import os
import sys
from pathlib import Path
from xml.etree import ElementTree as ET

module = sys.argv[1]
if module not in {f"task{i:02}" for i in range(1, 6)}:
    raise SystemExit("Unknown task")
files = sorted(Path(module, "build/test-results/test").glob("TEST-*.xml"))
lines = [f"### {module}", ""]
if not files:
    lines += ["Тесты не запустились. Откройте шаг «Проверить решение»: возможна ошибка компиляции, загрузки зависимостей или настройки Java."]
else:
    cases = [case for file in files for case in ET.parse(file).getroot().iter("testcase")]
    failed = sum(case.find("failure") is not None or case.find("error") is not None for case in cases)
    skipped = sum(case.find("skipped") is not None for case in cases)
    lines += [f"Пройдено: **{len(cases) - failed - skipped}/{len(cases)}**. Ошибок: {failed}. Пропущено: {skipped}.", ""]
    for case in cases:
        failure = case.find("failure")
        if failure is None:
            failure = case.find("error")
        icon = "❌" if failure is not None else ("⏭️" if case.find("skipped") is not None else "✅")
        lines.append(f"- {icon} {html.escape(case.get('name', 'test'))}")
        if failure is not None:
            lines.append(f"  <pre>{html.escape(failure.get('message', '')[:1500])}</pre>")
    lines += ["", "`NotImplementedError` означает, что в решении ещё есть TODO. Красные проверки нерешённых задач ожидаемы."]
text = "\n".join(lines) + "\n"
print(text, end="")
if os.environ.get("GITHUB_STEP_SUMMARY"):
    with open(os.environ["GITHUB_STEP_SUMMARY"], "a", encoding="utf-8") as output:
        output.write(text)
