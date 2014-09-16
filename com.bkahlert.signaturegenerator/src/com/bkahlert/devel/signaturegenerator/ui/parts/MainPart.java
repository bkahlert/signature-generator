package com.bkahlert.devel.signaturegenerator.ui.parts;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.bkahlert.devel.nebula.widgets.RoundedComposite;
import com.bkahlert.devel.signaturegenerator.SignatureGenerator;
import com.bkahlert.devel.signaturegenerator.model.ConfigFactory;
import com.bkahlert.devel.signaturegenerator.ui.model.ConfigList;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.UpdateListStrategy;

public class MainPart {
	private DataBindingContext m_bindingContext;

	@Inject
	private Shell shell;

	private Text text;

	private File templateDirectory = null;
	private ConfigList configs = new ConfigList();
	private List list;

	public MainPart() {
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		FillLayout fillLayout = new FillLayout(SWT.HORIZONTAL);
		fillLayout.marginWidth = 10;
		fillLayout.marginHeight = 10;
		parent.setLayout(fillLayout);

		Composite composite = new RoundedComposite(parent, SWT.BORDER);
		composite.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		composite.setLayout(new FormLayout());

		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog directoryDialog = new DirectoryDialog(shell);
				directoryDialog.setText("Choose Signature Template Directory");
				directoryDialog
						.setMessage("Please choose the directory where signature templates can be found:");
				directoryDialog.setFilterPath(text.getText());
				String path = directoryDialog.open();
				text.setText(path);
				text.setSelection(path.length());
			}
		});
		FormData fd_btnNewButton = new FormData();
		fd_btnNewButton.right = new FormAttachment(100, -35);
		fd_btnNewButton.left = new FormAttachment(100, -209);
		fd_btnNewButton.top = new FormAttachment(0, 36);
		btnNewButton.setLayoutData(fd_btnNewButton);
		btnNewButton.setText("Choose Template Directory");

		text = new Text(composite, SWT.BORDER);
		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				templateDirectory = new File(text.getText());
				File[] configFiles = SignatureGenerator
						.getConfigFiles(templateDirectory);
				ConfigList configs = new ConfigList();
				for (File configFile : configFiles) {
					try {
						configs.add(ConfigFactory.readFromFile(configFile));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				MainPart.this.configs = configs;
			}
		});
		FormData fd_text = new FormData();
		fd_text.left = new FormAttachment(0, 40);
		fd_text.right = new FormAttachment(btnNewButton, -6);
		fd_text.top = new FormAttachment(0, 40);
		text.setLayoutData(fd_text);

		Button btnGenerateSignatures = new Button(composite, SWT.NONE);
		btnGenerateSignatures.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog directoryDialog = new DirectoryDialog(shell);
				directoryDialog.setText("Choose Signature Directory");
				directoryDialog
						.setMessage("Please choose the directory where the generated signatures shall be saved to:");
				// directoryDialog.setFilterPath(text.getText());
				String path = directoryDialog.open();
				File dir = new File(path);
				if (!dir.isDirectory()) {
					return;
				}

				// SignatureGenerator signatureGenerator = new
				// SignatureGenerator(signatureDirectory, config)
			}
		});
		FormData fd_btnGenerateSignatures = new FormData();
		fd_btnGenerateSignatures.top = new FormAttachment(btnNewButton, 6);
		fd_btnGenerateSignatures.right = new FormAttachment(btnNewButton, 0,
				SWT.RIGHT);
		fd_btnGenerateSignatures.left = new FormAttachment(btnNewButton, 0,
				SWT.LEFT);
		btnGenerateSignatures.setLayoutData(fd_btnGenerateSignatures);
		btnGenerateSignatures.setText("Generate Signatures");

		list = new List(composite, SWT.BORDER);
		FormData fd_list = new FormData();
		fd_list.bottom = new FormAttachment(text, 194, SWT.BOTTOM);
		fd_list.top = new FormAttachment(text, 11);
		fd_list.right = new FormAttachment(btnGenerateSignatures, -6);
		fd_list.left = new FormAttachment(0, 40);
		list.setLayoutData(fd_list);
		m_bindingContext = initDataBindings();

	}

	@PreDestroy
	public void dispose() {
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableList itemsListObserveWidget = WidgetProperties.items().observe(list);
		IObservableList namesConfigsObserveList = PojoProperties.list("names").observe(configs);
		bindingContext.bindList(itemsListObserveWidget, namesConfigsObserveList, null, new UpdateListStrategy(UpdateListStrategy.POLICY_NEVER));
		//
		return bindingContext;
	}
}
